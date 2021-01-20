package github.hmasum18.architecture.Repository;

public interface Repository<T> {
    Iterable<T> query(Criteria specification);
}
