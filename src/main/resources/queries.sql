-- writing old initiators
insert into bill_user (created_at, bill_id, user_id) select created_at, id, created_by_id from bill;

-- writing old participants
insert into bill_user (created_at, user_id, bill_id)
select max(item_participant.created_at) as created_at, item_participant.user_id, bill_item.bill_id
from bill_item, item_participant
where bill_item.id = item_participant.item_id and
  not exists (select user_id, bill_id
              from bill_user
              where user_id = item_participant.user_id and bill_id = bill_item.bill_id
              group by user_id, bill_id)
group by item_participant.user_id, bill_item.bill_id;
