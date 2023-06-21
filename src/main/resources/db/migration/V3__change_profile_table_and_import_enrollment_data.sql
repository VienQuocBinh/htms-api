update public.profile
set status = null;

ALTER TABLE public.profile
    ADD CONSTRAINT profile_status_check CHECK (
            (status)::text = ANY
            ((ARRAY ['FINISHED'::character varying, 'STUDYING'::character varying, 'DROPPED_OUT'::character varying, 'PENDING'::character varying])::text[])
        );

update public.profile
set status = 'PENDING';

insert into public.enrollment (is_cancelled, enrollment_date, class_id, trainee_id, cancel_reason, status)
values (false, '2023-06-13 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '00bcab59-5ddd-4528-b811-d85a8f0e27f7', null, 'PENDING'),
       (false, '2023-06-01 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '5028a606-554d-4c9b-9f43-a0f232a39f80', null, 'PENDING'),
       (false, '2023-06-10 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '399d6a3a-125e-4fb3-8727-894cb10b1e27', null, 'PENDING'),
       (false, '2023-06-19 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        'fce12db6-ebcc-4d60-bc6b-a357e8a96114', null, 'PENDING'),
       (false, '2023-06-10 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        'db29c64c-7b2f-4263-abd6-aa8f200bd3bb', null, 'PENDING'),
       (false, '2023-06-01 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '82c72928-2a83-4053-a62f-3ae816fb32da', null, 'PENDING'),
       (false, '2023-06-19 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        'bae2f78d-4aa0-4b61-8d73-44fa9162e005', null, 'PENDING'),
       (false, '2023-06-01 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '77d3f183-3e43-42c7-b1ac-1fb3c600a751', null, 'PENDING'),
       (false, '2023-06-10 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '82899e18-5dec-420e-b4e4-bf1be6dee534', null, 'PENDING'),
       (false, '2023-06-19 13:59:36.000000', '9bf2cc4b-4edc-453d-84ba-331aacfaf097',
        '673e3d95-bdac-426f-ab4b-4acb0a85554b', null, 'PENDING');