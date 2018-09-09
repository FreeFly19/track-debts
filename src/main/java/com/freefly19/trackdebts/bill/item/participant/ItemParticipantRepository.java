package com.freefly19.trackdebts.bill.item.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemParticipantRepository extends JpaRepository<ItemParticipant, Long> {
    Optional<ItemParticipant> findByUserIdAndItemId(long userId, long itemId);
}
