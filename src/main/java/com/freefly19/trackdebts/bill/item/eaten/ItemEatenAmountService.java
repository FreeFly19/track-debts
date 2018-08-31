package com.freefly19.trackdebts.bill.item.eaten;

import com.freefly19.trackdebts.bill.item.BillItem;
import com.freefly19.trackdebts.bill.item.BillItemRepository;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemEatenAmountService {
    private final ItemEatenAmountRepository itemEatenAmountRepository;
    private final BillItemRepository billItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Optional<String> specifyEatenAmount(long itemId, ItemEatenAmountCommand command, UserRequestContext context) {
        Optional<BillItem> billItem = billItemRepository.findById(itemId);
        if(!billItem.isPresent()) {
            return Optional.of("Item with " + itemId + " id not found");
        }

        ItemEatenAmount itemEatenAmount = ItemEatenAmount.builder()
                .item(billItem.get())
                .createdAt(context.timestamp())
                .user(context.toUser(userRepository))
                .value(new BigDecimal(command.getAmount()))
                .build();

        itemEatenAmountRepository.save(itemEatenAmount);

        return Optional.empty();
    }
}
