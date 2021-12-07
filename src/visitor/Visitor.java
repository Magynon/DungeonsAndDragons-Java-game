package visitor;

import characters.Entity;

public interface Visitor<T extends Entity> {
    void visit(T entity);
}
