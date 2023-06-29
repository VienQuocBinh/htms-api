insert into public.class_approval (created_date, id, class_id, created_by, comment, status)
values ('2023-06-11 21:19:23.000000', 12, '65f6e65a-7cdf-4fd6-9ce5-c5f80f42f6ca',
        '3bdb9fdd-40a0-4bd4-93aa-3462c2164d08', null, 'APPROVE');

update enrollment
set status   = 'APPROVE',
    class_id = 'bf1b2735-77c2-464d-90ec-b4f0f758f7ed'
where enrollment.class_id = '9bf2cc4b-4edc-453d-84ba-331aacfaf097';
