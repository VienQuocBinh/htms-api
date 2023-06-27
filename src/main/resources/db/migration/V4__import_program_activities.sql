INSERT INTO public.program_content (program_id, trainer_id)
VALUES ('73574861-62eb-4965-9ebc-cecbb50ea11f', '3ae6a7fb-87a4-423e-8f38-d1313e710a00');
INSERT INTO public.program_content (program_id, trainer_id)
VALUES ('73574861-62eb-4965-9ebc-cecbb50ea11f', 'ff5a3c78-2642-4d4d-a793-b2cabdabde17');
INSERT INTO public.program_content (program_id, trainer_id)
VALUES ('c2c6401d-b0ab-42b8-af7d-980260fcd963', '3ae6a7fb-87a4-423e-8f38-d1313e710a00');
INSERT INTO public.program_content (program_id, trainer_id)
VALUES ('c2c6401d-b0ab-42b8-af7d-980260fcd963', 'ff5a3c78-2642-4d4d-a793-b2cabdabde17');

INSERT INTO public.enrollment (is_cancelled, enrollment_date, class_id, trainee_id, cancel_reason, status)
VALUES (false, '2023-06-19 20:48:46.541000', '65f6e65a-7cdf-4fd6-9ce5-c5f80f42f6ca',
        '399d6a3a-125e-4fb3-8727-894cb10b1e27', null, 'APPROVE');
INSERT INTO public.enrollment (is_cancelled, enrollment_date, class_id, trainee_id, cancel_reason, status)
VALUES (false, '2023-06-19 20:48:46.541000', '281484f9-1711-40d6-ad73-d5c34ede8284',
        'fce12db6-ebcc-4d60-bc6b-a357e8a96114', null, 'APPROVE');

INSERT INTO public.topic (cycle_id, id, program_id, trainer_id, description, name)
VALUES (null, 'a64edcb5-a30b-47c5-a053-332cd2a10f6b', '73574861-62eb-4965-9ebc-cecbb50ea11f',
        '3ae6a7fb-87a4-423e-8f38-d1313e710a00', 'Gửi các bác sĩ tài liệu của khóa học này:', 'Tài liệu học tập');

INSERT INTO public.activity (id, topic_id, description, material_link, name)
VALUES ('74008bab-c1b5-4a5c-b265-eb017c9b4310', 'a64edcb5-a30b-47c5-a053-332cd2a10f6b', 'Link powerpoint',
        'https://fake.link.com', 'Link powerpoint');

INSERT INTO public.assignment (grade, due_date, start_date, activity_id, id, attachments, name, status)
VALUES (null, '2023-06-26 22:12:18.000000', '2023-06-18 22:12:23.000000', '74008bab-c1b5-4a5c-b265-eb017c9b4310',
        'c96da5e8-3df9-47ef-afe4-eeb48669d96d', null, 'Assignment 1', 'NOT_STARTED');
