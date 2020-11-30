package  Module ;
//Class Agent, class principale qui joue l'interface avec l'utilisateur
//pour spécifique voir diagramme
//gestion de l'utilsiateur
//vérification de pseudo
//activation de fenetre de ChatSession etc.



public class Agent{
   private MulticastReceiver MR;
   private MulticastSender MS;
   Agent(User user){
	   this.MR = new MulticastReceiver(user);
	   this.MS = new MulticastSender(user);
   }
	public MulticastReceiver getMR() {
		return MR;
	}
	
	public MulticastSender getMS() {
		return MS;
	}

   
   
}
