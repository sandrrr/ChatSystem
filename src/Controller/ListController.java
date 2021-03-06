package Controller;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListController<T> implements Iterable<T> {
    private final ListProperty<T> list;
    private ListChangeListener<? super T> listListener;

    interface OnAdded<T> {
        void onAdded(T element);
    }

    interface OnRemoved<T> {
        void onRemoved(T element);
    }

    public ListController() {
        list = new SimpleListProperty<>();
        list.set(FXCollections.observableArrayList());
    }

    public void addListener(OnAdded<T> onAdded, OnRemoved<T> onRemoved) {
        removeListener();

        listListener = e -> {
            while (e.next()) {
                for (T element : e.getAddedSubList()) {
                    Platform.runLater(() -> onAdded.onAdded(element));
                }
                for (T element : e.getRemoved()) {
                    Platform.runLater(() -> onRemoved.onRemoved(element));
                }
            }
        };
        list.addListener(listListener);
    }

    public void removeListener() {
        if (listListener != null) {
            list.removeListener(listListener);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return list.get().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        list.get().forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return list.get().spliterator();
    }

    public void add(T x) {
        list.get().add(x);
    }

    public void remove(T x) {
        list.get().remove(x);
    }

    public void clear() {
        list.get().clear();
    }

    public T first() {
        return list.get().get(0);
    }

    public T last() {
        if (list.get().isEmpty()) return null;
        return list.get().get(list.get().size() - 1);
    }

    @Override
    public String toString() {
        return list.get().toString();
    }
}
