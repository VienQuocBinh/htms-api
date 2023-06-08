insert into public.role (id, name)
values (1, 'admin');
insert into public.department (id, name)
values ('e71ac0a8-fe10-40f9-a831-94bc0703641d', 'Khoa Nhi'),
       ('ef0aee96-d60f-422c-9418-0487d35e530e', 'Khoa Nội');
insert into public.account (is_deleted, created_date, role_id, updated_date, created_by, department_id, id, updated_by,
                            email, title)
values (false, '2023-06-06 20:48:46.541000', 1, '2023-06-06 20:48:46.541000', 'f858aa18-9569-4cfd-b038-8d5ee807f94d',
        'e71ac0a8-fe10-40f9-a831-94bc0703641d', '3bdb9fdd-40a0-4bd4-93aa-3462c2164d08', null, 'taict@gmail.com', 'BS'),
       (false, '2023-06-06 20:48:46.539000', 1, '2023-06-06 20:48:46.539000', 'f1a02256-ef4c-4b74-9ce7-25cf6b9cee20',
        'ef0aee96-d60f-422c-9418-0487d35e530e', '4d2196da-097f-4fc5-9bb2-a40ef2ac7b2e', null, 'huyce@gmail.com', 'ĐD');;
insert into public.cycle (end_date, start_date, vacation_end_date, vacation_start_date, id, description)
values ('2023-06-06 21:15:10.580000', '2023-06-06 21:15:10.580000', '2023-06-06 21:15:10.580000',
        '2023-06-06 21:15:10.580000', 'd99ae531-4845-4b8b-b826-829b5c5f032b', 'Spring 2023'),
       ('2023-06-07 01:16:09.000000', '2023-01-07 01:16:14.000000', '2023-06-07 01:16:19.000000',
        '2023-06-07 01:16:25.000000', 'a64edcb5-a30b-47c5-a053-332cd2a10f6b', 'Summer 2023');
insert into public.program (department_id, id, code, description)
values ('e71ac0a8-fe10-40f9-a831-94bc0703641d', '73574861-62eb-4965-9ebc-cecbb50ea11f', 'SP23', 'Coursera program'),
       ('ef0aee96-d60f-422c-9418-0487d35e530e', 'c2c6401d-b0ab-42b8-af7d-980260fcd963', 'SU23', 'Hệ tim');
insert into public.program_per_cycle (program_end_date, program_start_date, cycle_id, program_id)
values ('2023-08-06 22:06:07.000000', '2023-06-06 22:06:05.000000', 'd99ae531-4845-4b8b-b826-829b5c5f032b',
        '73574861-62eb-4965-9ebc-cecbb50ea11f'),
       ('2023-11-06 22:06:31.000000', '2023-06-06 22:06:26.000000', 'd99ae531-4845-4b8b-b826-829b5c5f032b',
        'c2c6401d-b0ab-42b8-af7d-980260fcd963'),
       ('2022-12-07 01:17:54.000000', '2022-06-07 01:17:49.000000', 'a64edcb5-a30b-47c5-a053-332cd2a10f6b',
        'c2c6401d-b0ab-42b8-af7d-980260fcd963');
insert into public.class (max_quantity, min_quantity, cycle_id, id, program_id, code, name, reason, status)
values (29, 3, 'd99ae531-4845-4b8b-b826-829b5c5f032b', 'a64edcb5-a30b-47c5-a053-332cd2a10f6b',
        'c2c6401d-b0ab-42b8-af7d-980260fcd963', null, 'SE1615', null, 'PENDING'),
       (30, 4, 'd99ae531-4845-4b8b-b826-829b5c5f032b', 'eefb61eb-115a-4f7e-95c9-64e51ca93149',
        '73574861-62eb-4965-9ebc-cecbb50ea11f', null, 'SE1611', null, 'PENDING'),
       (30, 5, 'd99ae531-4845-4b8b-b826-829b5c5f032b', '62229e43-984c-4ac5-8662-c1c98de50e91',
        '73574861-62eb-4965-9ebc-cecbb50ea11f', null, 'SE1666', null, 'PENDING');

insert into public.permission (id, role_id, permission, resource)
values (1, 1, 'FULL_CONTROL', 'CLASS'),
       (2, 1, 'FULL_CONTROL', 'MATERIAL');
insert into public.profile (is_deleted, created_date, updated_date, created_by, id, updated_by, status)
values (false, '2023-06-06 22:02:58.000000', '2023-06-06 22:03:03.000000', '03a1ed7d-11aa-4206-bb41-024023c00a78',
        '03a1ed7d-11aa-4206-bb41-024023c00a78', null, 'ACTIVE');


insert into public.trainee (is_deleted, birthdate, created_date, updated_date, account_id, created_by, id, profile_id,
                            updated_by, name, phone, code)
values (false, '2023-06-06 22:04:11.000000', '2023-06-06 22:04:14.000000', null, '3bdb9fdd-40a0-4bd4-93aa-3462c2164d08',
        '673e3d95-bdac-426f-ab4b-4acb0a85554b', '673e3d95-bdac-426f-ab4b-4acb0a85554b',
        '03a1ed7d-11aa-4206-bb41-024023c00a78', null, 'Vien Quoc Binh', '0986587563', 'BINHVQ');

insert into public.trainer (is_deleted, birthdate, created_date, updated_date, account_id, created_by, id, updated_by,
                            name, phone, code)
values (false, '1988-06-05 07:00:00.000000', '2023-06-06 20:50:27.953000', '2023-06-06 20:50:27.953000',
        '3bdb9fdd-40a0-4bd4-93aa-3462c2164d08', '3bdb9fdd-40a0-4bd4-93aa-3462c2164d08',
        '3ae6a7fb-87a4-423e-8f38-d1313e710a00', null, 'KhanhKT', '0986574526', 'KHANHKT'),
       (false, '2023-06-05 07:00:00.000000', '2023-06-06 20:49:56.500000', '2023-06-06 20:49:56.500000',
        '4d2196da-097f-4fc5-9bb2-a40ef2ac7b2e', '4d2196da-097f-4fc5-9bb2-a40ef2ac7b2e',
        'ff5a3c78-2642-4d4d-a793-b2cabdabde17', null, 'John', '0986563256', 'John');

insert into public.enrollment (is_cancelled, enrollment_date, cycle_id, program_id, trainee_id, cancel_reason)
values (false, '2023-06-08 16:16:42.000000', 'd99ae531-4845-4b8b-b826-829b5c5f032b',
        '73574861-62eb-4965-9ebc-cecbb50ea11f', '673e3d95-bdac-426f-ab4b-4acb0a85554b', null);

