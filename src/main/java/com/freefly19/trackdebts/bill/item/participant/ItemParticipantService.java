package com.freefly19.trackdebts.bill.item.participant;

import com.freefly19.trackdebts.bill.item.BillItem;
import com.freefly19.trackdebts.bill.item.BillItemRepository;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemParticipantService {
    private final ItemParticipantRepository itemParticipantRepository;
    private final BillItemRepository billItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Optional<String> specifyCoefficient(long itemId, ItemParticipantCommand command, UserRequestContext context) {
        Optional<BillItem> oItem = billItemRepository.findById(itemId);
        if(!oItem.isPresent()) {
            return Optional.of("Item with " + itemId + " id not found");
        }

        BillItem item = oItem.get();

        if (item.getBill().getBillLock() != null && item.getBill().getBillLock().getId() != null) {
            return Optional.of("You cannot change coefficient for items in locked bills");
        }

        ItemParticipant participant = itemParticipantRepository.findByUserIdAndItemId(context.getId(), itemId)
                .orElseGet(() -> ItemParticipant.builder()
                        .item(item)
                        .createdAt(context.timestamp())
                        .user(context.toUser(userRepository))
                        .build());

        participant.setCoefficient(command.getCoefficient());

        if (command.getCoefficient().equals(BigDecimal.ZERO)) {
            itemParticipantRepository.delete(participant);
        } else {
            itemParticipantRepository.save(participant);
        }

        return Optional.empty();
    }
}
