package Model;

import Controller.ListController;
import Launcher.Launcher;
import Launcher.Main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

//Communication en mode broadcast
//principalement utilisé pour 'vérifier doublante de pseudo'
//ou pour communiquer entre plusieur personnes
public class CommunicationMulticast implements Runnable {
    private InetAddress addressIP;
    private MulticastSocket socket;
    private final ListController<User> userList = new ListController<>();

    public CommunicationMulticast() {
        try {
            this.addressIP = InetAddress.getByName("224.0.0.1");
            this.socket = new MulticastSocket(52000);
            socket.joinGroup(addressIP);

            //DEBUG
            Launcher.printDebug("Multicast group joined");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //DEBUG
        Launcher.printDebug("Multicast server started");

        byte[] message = new byte[1024];
        //String data;
        DatagramPacket packet = new DatagramPacket(message, message.length);

        //Clear the socket buffer
        clearSocketBuffer(packet);

        while (Main.getUser().isConnected()) {
            try {
                socket.receive(packet);
                MulticastPacket mcPacket = new MulticastPacket(message);

                if (mcPacket.addrMac.equals(Main.getUser().getAddressMAC())) continue;

                //DEBUG
                Launcher.printDebug("M-R: " + mcPacket);

                switch (mcPacket.protocol) {
                    case "newUser":
                        if (verifyUsername(mcPacket.data)) {
                            ChatSession chatSession = new ChatSession(new Socket(packet.getAddress(), CommunicationUnicast.port ));
                            chatSession.sendMessage(new MulticastPacket("newUser", Main.getUser().getUsername()).toString(), false);
                            if (!Main.getUser().getUsername().equals(mcPacket.data)) {
                                userList.add(new User(mcPacket.data, packet.getAddress(), mcPacket.addrMac, chatSession));
                                Database.get_messages(mcPacket.addrMac,chatSession.getMessageList()); //database update
                            } else {
                                chatSession.close();
                            }
                        }
                        break;
                    case "editUser":
                        for (User user : userList) {
                            if (user.getAddressMAC().equals(mcPacket.addrMac)) {
                                user.setUsername(mcPacket.data);
                                break;
                            }
                        }
                        break;
                    case "removeUser":
                        for (User user : userList) {
                            if (user.getAddressMAC().equals(mcPacket.addrMac)) {
                                user.getChatSession().close();
                                userList.remove(user);
                                break;
                            }
                        }
                        break;
                }

                //DEBUG
                Launcher.printDebug(userList.toString());
            } catch (IOException e) {
                //Socket closed;
            }
        }

        //DEBUG
        Launcher.printDebug("Multicast server stopped");
    }

    private void send(MulticastPacket packet) {
        try {
            byte[] message;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            (new DataOutputStream(out)).writeUTF(packet.toString());
            message = out.toByteArray();
            socket.send(new DatagramPacket(message, message.length, addressIP, socket.getLocalPort()));

            //DEBUG
            Launcher.printDebug("M-S: " + packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ListController<User> getUserList() {
        return userList;
    }

    public void initUserList() {
        userList.clear();
        send(new MulticastPacket("newUser", Main.getUser().getUsername()));
    }

    public boolean verifyUsername(String username) {
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    public void editUsername(String username) throws Exception {
        if (verifyUsername(username)) {
            Main.getUser().setUsername(username);
            send(new MulticastPacket("editUser", username));
        }
        throw new Exception("Username already taken");
    }

    private void clearSocketBuffer(DatagramPacket packet) {
        boolean bufferEmpty = false;
        try {
            socket.setSoTimeout(1);
            while (!bufferEmpty) {
                try {
                    socket.receive(packet);
                } catch (SocketTimeoutException e) {
                    //Buffer empty
                    bufferEmpty = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket.setSoTimeout(0);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        userList.removeListener();
        Main.getUnicast().closeAllChatSession();
        send(new MulticastPacket("removeUser", ""));
        Main.getUser().setIsConnected(false);
    }

    public void close() {
        if (Main.getUser().isConnected()) {
            logout();
        }
        try {
            socket.leaveGroup(addressIP);
            socket.close();

            //DEBUG
            Launcher.printDebug("Multicast group leaved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
