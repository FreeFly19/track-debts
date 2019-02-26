-- writing old initiators
insert into bill_user (created_at, bill_id, user_id) select created_at, id, created_by_id from bill;

-- writing old participants
-- how to delete duplicates?
insert into bill_user (created_at, user_id, bill_id)
select max(item_participant.created_at) as created_at, item_participant.user_id, bill_item.bill_id
from bill_item, item_participant
where bill_item.id = item_participant.item_id
group by item_participant.user_id, bill_item.bill_id;
