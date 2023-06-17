create sequence public.option_seq
    increment by 50;

alter sequence public.option_seq owner to "user";

create table public.changed_schedule
(
    created_date       timestamp(6) not null,
    from_date          timestamp(6) not null,
    last_modified_date timestamp(6),
    to_date            timestamp(6) not null,
    changed_by         uuid         not null,
    from_trainer       uuid         not null,
    id                 uuid         not null
        constraint changed_schedule_pkey
            primary key,
    to_trainer         uuid         not null,
    comment            varchar(255),
    reason             varchar(255)
);

alter table public.changed_schedule
    owner to "user";

create table public.cycle
(
    duration    integer,
    id          uuid         not null
        constraint cycle_pkey
            primary key,
    description varchar(1000),
    name        varchar(255) not null
);

alter table public.cycle
    owner to "user";

create table public.department
(
    id   uuid         not null
        constraint department_pkey
            primary key,
    name varchar(100) not null,
    code varchar(255)
        constraint department_code_key
            unique
);

alter table public.department
    owner to "user";

create table public.profile
(
    is_deleted   boolean      not null,
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    created_by   uuid         not null,
    id           uuid         not null
        constraint profile_pkey
            primary key,
    updated_by   uuid,
    status       varchar(255)
);

alter table public.profile
    owner to "user";

create table public.question
(
    id                  serial
        constraint question_pkey
            primary key,
    mark                double precision
        constraint question_mark_check
            check ((mark >= (0)::double precision) AND (mark <= (10)::double precision)),
    single_answer_input char not null,
    title               varchar(255)
);

alter table public.question
    owner to "user";

create table public.option
(
    id              integer not null
        constraint option_pkey
            primary key,
    is_right_answer boolean,
    question_id     integer
        constraint fkgtlhwmagte7l2ssfsgw47x9ka
            references public.question
);

alter table public.option
    owner to "user";

create table public.role
(
    id   bigserial
        constraint role_pkey
            primary key,
    name varchar(255) not null
);

alter table public.role
    owner to "user";

create table public.account
(
    is_deleted    boolean      not null,
    created_date  timestamp(6) not null,
    role_id       bigint
        constraint fkd4vb66o896tay3yy52oqxr9w0
            references public.role,
    updated_date  timestamp(6),
    created_by    uuid         not null,
    department_id uuid
        constraint fk5l70ejb7b4p3fvj8as9clgtwo
            references public.department,
    id            uuid         not null
        constraint account_pkey
            primary key,
    updated_by    uuid,
    email         varchar(255),
    title         varchar(255) not null
);

alter table public.account
    owner to "user";

create table public.permission
(
    id         bigserial
        constraint permission_pkey
            primary key,
    role_id    bigint
        constraint fkrvhjnns4bvlh4m1n97vb7vbar
            references public.role,
    permission varchar(255)
        constraint permission_permission_check
            check ((permission)::text = ANY
                   ((ARRAY ['READ'::character varying, 'WRITE'::character varying, 'MODIFY'::character varying, 'FULL_CONTROL'::character varying, 'NO_CONTROL'::character varying])::text[])),
    resource   varchar(255)
        constraint permission_resource_check
            check ((resource)::text = ANY
                   ((ARRAY ['MATERIAL'::character varying, 'CLASS'::character varying, 'PROGRAM'::character varying, 'SYLLABUS'::character varying])::text[]))
);

alter table public.permission
    owner to "user";

create table public.room
(
    max_capacity integer,
    id           uuid not null
        constraint room_pkey
            primary key,
    name         varchar(255)
);

alter table public.room
    owner to "user";

create table public.syllabus
(
    total_sessions smallint,
    id             uuid not null
        constraint syllabus_pkey
            primary key,
    description    varchar(1000),
    pass_criteria  varchar(1000)
);

alter table public.syllabus
    owner to "user";

create table public.assessment_scheme
(
    id          serial
        constraint assessment_scheme_pkey
            primary key,
    weight      double precision,
    syllabus_id uuid
        constraint fkrtqf7smtj6kp8u3y415kwkw51
            references public.syllabus,
    category    varchar(255)
        constraint assessment_scheme_category_check
            check ((category)::text = ANY
                   ((ARRAY ['ASSIGNMENT'::character varying, 'QUIZ'::character varying, 'FE'::character varying, 'PE'::character varying])::text[]))
);

alter table public.assessment_scheme
    owner to "user";

create table public.material
(
    id          uuid         not null
        constraint material_pkey
            primary key,
    syllabus_id uuid
        constraint fkfmuye2g2ot4vg2md2d7lhc5lc
            references public.syllabus,
    description varchar(1000),
    link        varchar(1000),
    name        varchar(255) not null
);

alter table public.material
    owner to "user";

create table public.module
(
    module_no   integer,
    id          uuid not null
        constraint module_pkey
            primary key,
    syllabus_id uuid
        constraint fk99595argxkb49s31xoikat3ti
            references public.syllabus,
    description varchar(1000),
    link        varchar(1000)
);

alter table public.module
    owner to "user";

create table public.program
(
    is_active     boolean,
    max_quantity  integer,
    min_quantity  integer,
    department_id uuid
        constraint fkbx2a0ta1c2ppgqcm9fa1ufftf
            references public.department,
    id            uuid         not null
        constraint program_pkey
            primary key,
    syllabus_id   uuid
        constraint program_syllabus_id_key
            unique
        constraint fk7m7244f62m5x8hphhvxuul53p
            references public.syllabus,
    description   varchar(1000),
    code          varchar(255) not null
        constraint program_code_key
            unique,
    name          varchar(255) not null
);

alter table public.program
    owner to "user";

create table public.question_bank
(
    id         uuid not null
        constraint question_bank_pkey
            primary key,
    program_id uuid
        constraint fknl05vr0525tkdrmeg3bye3n8b
            references public.program,
    password   varchar(255)
);

alter table public.question_bank
    owner to "user";

create table public.session
(
    session_no integer,
    id         uuid not null
        constraint session_pkey
            primary key,
    module_id  uuid
        constraint fk7vcm9xoec4eqifbkryow1dv1y
            references public.module
);

alter table public.session
    owner to "user";

create table public.trainee
(
    is_deleted   boolean      not null,
    birthdate    timestamp(6),
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    account_id   uuid
        constraint trainee_account_id_key
            unique
        constraint fkx5v2xc72p5f8jm2trjwswgu
            references public.account,
    created_by   uuid         not null,
    id           uuid         not null
        constraint trainee_pkey
            primary key,
    profile_id   uuid
        constraint trainee_profile_id_key
            unique
        constraint fkltl5tu7qk21at5ctvt7mc6v1c
            references public.profile,
    updated_by   uuid,
    code         varchar(255) not null
        constraint trainee_code_key
            unique,
    name         varchar(255) not null,
    phone        varchar(255) not null
);

alter table public.trainee
    owner to "user";

create table public.trainer
(
    is_deleted   boolean      not null,
    birthdate    timestamp(6),
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    account_id   uuid
        constraint trainer_account_id_key
            unique
        constraint fkmkspgds6yphi0blxbb7j6smv8
            references public.account,
    created_by   uuid         not null,
    id           uuid         not null
        constraint trainer_pkey
            primary key,
    updated_by   uuid,
    code         varchar(255) not null
        constraint trainer_code_key
            unique,
    name         varchar(255) not null,
    phone        varchar(255) not null
);

alter table public.trainer
    owner to "user";

create table public.class
(
    is_deleted       boolean      not null,
    quantity         integer,
    created_date     timestamp(6) not null,
    end_date         timestamp(6),
    start_date       timestamp(6),
    updated_date     timestamp(6),
    created_by       uuid         not null,
    cycle_id         uuid
        constraint fkh5cxykk8d435rfaw6umlwfx6r
            references public.cycle,
    id               uuid         not null
        constraint class_pkey
            primary key,
    program_id       uuid
        constraint fkcxcigxg6kx0x8l68uf5wonnot
            references public.program,
    trainer_id       uuid
        constraint fkpbf3k6sdqcs2v1vjqdc8rthqh
            references public.trainer,
    updated_by       uuid,
    code             varchar(255)
        constraint class_code_key
            unique,
    general_schedule varchar(255),
    name             varchar(255)
);

alter table public.class
    owner to "user";

create table public.class_approval
(
    created_date timestamp(6) not null,
    id           bigserial
        constraint class_approval_pkey
            primary key,
    class_id     uuid
        constraint fk47jaj62s2g40sxnc379ce0sxh
            references public.class,
    created_by   uuid,
    comment      varchar(255),
    status       varchar(255)
        constraint class_approval_status_check
            check ((status)::text = ANY
                   ((ARRAY ['REJECT'::character varying, 'APPROVE'::character varying, 'PENDING'::character varying])::text[]))
);

alter table public.class_approval
    owner to "user";

create table public.enrollment
(
    is_cancelled    boolean,
    enrollment_date timestamp(6) not null,
    class_id        uuid         not null
        constraint fk5v613v80qptj9japxdb9g9eti
            references public.class,
    trainee_id      uuid         not null
        constraint fkgbr3ng3rd30tmhyw0gc32oxli
            references public.trainee,
    cancel_reason   varchar(255),
    status          varchar(255)
        constraint enrollment_status_check
            check ((status)::text = ANY
                   ((ARRAY ['REJECT'::character varying, 'APPROVE'::character varying, 'PENDING'::character varying])::text[])),
    constraint enrollment_pkey
        primary key (class_id, trainee_id)
);

alter table public.enrollment
    owner to "user";

create table public.program_content
(
    program_id uuid not null
        constraint fkhyantod7sk5p9prqfjwa3nsmt
            references public.program,
    trainer_id uuid not null
        constraint fkhiaw95n71n7ribugtot6o2mwv
            references public.trainer,
    constraint program_content_pkey
        primary key (program_id, trainer_id)
);

alter table public.program_content
    owner to "user";

create table public.schedule
(
    is_deleted          boolean      not null,
    created_date        timestamp(6) not null,
    date                timestamp(6) not null,
    end_time            timestamp(6) not null,
    start_time          timestamp(6) not null,
    updated_date        timestamp(6),
    changed_schedule_id uuid
        constraint schedule_changed_schedule_id_key
            unique
        constraint fkd0retj13eb69vrcw1x4hueshs
            references public.changed_schedule,
    class_id            uuid
        constraint fkqqv2rqy5xxw2oyhie35seyclw
            references public.class,
    created_by          uuid         not null,
    id                  uuid         not null
        constraint schedule_pkey
            primary key,
    room_id             uuid
        constraint fkh2hdhbss2x31ns719hka6enma
            references public.room,
    trainer_id          uuid
        constraint schedule_trainer_id_key
            unique
        constraint fkl1u2pb62wun0njqsorvvlvoy8
            references public.trainer,
    updated_by          uuid
);

alter table public.schedule
    owner to "user";

create table public.attendance
(
    is_deleted   boolean      not null,
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    created_by   uuid         not null,
    id           uuid         not null
        constraint attendance_pkey
            primary key,
    schedule_id  uuid
        constraint fk3ubfa45l2ve7k80jlxkwh5ht5
            references public.schedule,
    trainee_id   uuid
        constraint fkghlbinj34ic6pwlx2nv1largn
            references public.trainee,
    updated_by   uuid
);

alter table public.attendance
    owner to "user";

create table public.topic
(
    cycle_id    uuid
        constraint fkpo6e27yrogqarkqplv0v4btn
            references public.cycle,
    id          uuid         not null
        constraint topic_pkey
            primary key,
    program_id  uuid,
    trainer_id  uuid,
    description varchar(1000),
    name        varchar(255) not null,
    constraint fkoug8eehyanv797cca6whinqe2
        foreign key (program_id, trainer_id) references public.program_content
);

alter table public.topic
    owner to "user";

create table public.activity
(
    id            uuid         not null
        constraint activity_pkey
            primary key,
    topic_id      uuid
        constraint fkjd7br9vd7q5joo2mrxn65wqpd
            references public.topic,
    description   varchar(1000),
    material_link varchar(1000),
    name          varchar(255) not null
);

alter table public.activity
    owner to "user";

create table public.assignment
(
    grade       double precision,
    due_date    timestamp(6),
    start_date  timestamp(6),
    activity_id uuid
        constraint fk9u5lq5fen5vcj7rh236cbsvjc
            references public.activity,
    id          uuid         not null
        constraint assignment_pkey
            primary key,
    attachments varchar(1000),
    name        varchar(255) not null,
    status      varchar(255)
        constraint assignment_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NOT_STARTED'::character varying, 'ON_GOING'::character varying, 'COMPLETED'::character varying, 'CLOSED'::character varying])::text[]))
);

alter table public.assignment
    owner to "user";

create table public.assignment_submission
(
    grade         double precision,
    created_date  timestamp(6),
    updated_date  timestamp(6),
    assignment_id uuid
        constraint fki9tdkyaqlb4j7qm7y2k74jd7o
            references public.assignment,
    id            uuid not null
        constraint assignment_submission_pkey
            primary key,
    file_link     varchar(1000)
);

alter table public.assignment_submission
    owner to "user";

create table public.test
(
    test_no     integer,
    date        timestamp(6) not null,
    id          bigserial
        constraint test_pkey
            primary key,
    time        bigint       not null,
    activity_id uuid
        constraint test_activity_id_key
            unique
        constraint fkrwli2wssa1escouia0simebth
            references public.activity,
    agenda      varchar(255),
    type        varchar(255)
        constraint test_type_check
            check ((type)::text = ANY
                   ((ARRAY ['ESSAY'::character varying, 'MULTIPLE_CHOICE'::character varying])::text[]))
);

alter table public.test
    owner to "user";

create table public.test_question
(
    question_id integer not null
        constraint fkk5qvcm9mkgbi8hm4u2mlidm4i
            references public.question,
    test_id     bigint  not null
        constraint fkk2sfq1wyx19uvwn7pkgk1bc9n
            references public.test
);

alter table public.test_question
    owner to "user";

create table public.test_score
(
    score      double precision,
    id         bigserial
        constraint test_score_pkey
            primary key,
    test_id    bigint
        constraint fkpapokkjbrc8brpowyr2u1rpyg
            references public.test,
    trainee_id uuid
        constraint fktpkhxuxe5l12ra7auyxvkjomp
            references public.trainee
);

alter table public.test_score
    owner to "user";

create table public.unit
(
    duration    integer,
    id          serial
        constraint unit_pkey
            primary key,
    unit_no     integer,
    session_id  uuid
        constraint fk3ltxu5g3m8paauww2nigsjh07
            references public.session,
    description varchar(1000),
    name        varchar(255)
);

alter table public.unit
    owner to "user";

