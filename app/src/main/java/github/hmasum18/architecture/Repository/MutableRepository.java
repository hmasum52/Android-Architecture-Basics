package github.hmasum18.architecture.Repository;

public interface MutableRepository<T> extends Repository<T>{
    void add(T item);
    //can be any collection as they extends are iterable
    void add(Iterable<T> items);
    void update(T item);
    void remove(T item);
}
