package io.ariku.owner;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface OwnerRecordRepository {
    void store(OwnerRecord ownerRecord);
    Optional<OwnerRecord> get(String owner);
    List<OwnerRecord> list(OwnerRecord ownerRecord);
    void update(OwnerRecord ownerRecord);
    void delete(String owner);
}
