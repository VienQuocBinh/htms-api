create sequence public.option_seq
    increment by 50;

alter sequence public.option_seq owner to "user";

create table if not exists public.flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table public.flyway_schema_history
    owner to "user";

create index if not exists flyway_schema_history_s_idx
    on public.flyway_schema_history (success);

create table if not exists public.cycle
(
    end_date            timestamp(6),
    start_date          timestamp(6),
    vacation_end_date   timestamp(6),
    vacation_start_date timestamp(6),
    id                  uuid not null
        primary key,
    description         varchar(255)
);

alter table public.cycle
    owner to "user";

create table if not exists public.department
(
    id   uuid         not null
        primary key,
    name varchar(100) not null
);

alter table public.department
    owner to "user";

create table if not exists public.profile
(
    is_deleted   boolean      not null,
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    created_by   uuid         not null,
    id           uuid         not null
        primary key,
    updated_by   uuid,
    status       varchar(255)
);

alter table public.profile
    owner to "user";

create table if not exists public.program
(
    department_id uuid
        constraint fkbx2a0ta1c2ppgqcm9fa1ufftf
            references public.department,
    id            uuid         not null
        primary key,
    code          varchar(255) not null,
    description   varchar(255)
);

alter table public.program
    owner to "user";

create table if not exists public.program_per_cycle
(
    program_end_date   timestamp(6),
    program_start_date timestamp(6),
    cycle_id           uuid not null
        constraint fkos1g39css99qo4qi85yg3o73n
            references public.cycle,
    program_id         uuid not null
        constraint fkk3oh2iax8p215dki1sx45qf2x
            references public.program,
    primary key (cycle_id, program_id)
);

alter table public.program_per_cycle
    owner to "user";

create table if not exists public.class
(
    quantity   integer not null
        constraint class_quantity_check
            check (quantity <= 30),
    cycle_id   uuid,
    id         uuid    not null
        primary key,
    program_id uuid,
    name       varchar(255),
    status     varchar(255)
        constraint class_status_check
            check ((status)::text = ANY
                   ((ARRAY ['PENDING'::character varying, 'ACCEPTED'::character varying, 'REJECTED'::character varying])::text[])),
    constraint fk9n6uknlyt5djr4cvqcxey9v84
        foreign key (cycle_id, program_id) references public.program_per_cycle
);

alter table public.class
    owner to "user";

create table if not exists public.question_bank
(
    id         uuid not null
        primary key,
    subject_id uuid,
    password   varchar(255)
);

alter table public.question_bank
    owner to "user";

create table if not exists public.question
(
    id                  serial
        primary key,
    mark                double precision not null
        constraint question_mark_check
            check ((mark >= (0)::double precision) AND (mark <= (10)::double precision)),
    single_answer_input char             not null,
    question_bank_id    uuid
        constraint fkejbwnygbsv82ocl8dq6o2k6yq
            references public.question_bank,
    title               varchar(255)
);

alter table public.question
    owner to "user";

create table if not exists public.option
(
    id              integer not null
        primary key,
    is_right_answer boolean,
    option_char     char,
    question_id     integer
        constraint fkgtlhwmagte7l2ssfsgw47x9ka
            references public.question
);

alter table public.option
    owner to "user";

create table if not exists public.role
(
    id   bigserial
        primary key,
    name varchar(255) not null
);

alter table public.role
    owner to "user";

create table if not exists public.account
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
        primary key,
    updated_by    uuid,
    email         varchar(255),
    title         varchar(255) not null
);

alter table public.account
    owner to "user";

create table if not exists public.permission
(
    id         bigserial
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

create table if not exists public.room
(
    id uuid not null
        primary key
);

alter table public.room
    owner to "user";

create table if not exists public.schedule
(
    is_deleted   boolean      not null,
    created_date timestamp(6) not null,
    date         timestamp(6) not null,
    end_time     timestamp(6) not null,
    start_time   timestamp(6) not null,
    updated_date timestamp(6),
    created_by   uuid         not null,
    id           uuid         not null
        primary key,
    room_id      uuid
        unique
        constraint fkh2hdhbss2x31ns719hka6enma
            references public.room,
    updated_by   uuid
);

alter table public.schedule
    owner to "user";

create table if not exists public.syllabus
(
    total_sessions smallint,
    id             uuid not null
        primary key,
    description    varchar(255),
    pass_criteria  varchar(255)
);

alter table public.syllabus
    owner to "user";

create table if not exists public.assessment_scheme
(
    id          serial
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

create table if not exists public.material
(
    id          uuid         not null
        primary key,
    syllabus_id uuid
        constraint fkfmuye2g2ot4vg2md2d7lhc5lc
            references public.syllabus,
    description varchar(255),
    link        varchar(255),
    name        varchar(255) not null
);

alter table public.material
    owner to "user";

create table if not exists public.module
(
    module_no   smallint,
    id          uuid not null
        primary key,
    syllabus_id uuid
        constraint fk99595argxkb49s31xoikat3ti
            references public.syllabus,
    description varchar(255),
    link        varchar(255)
);

alter table public.module
    owner to "user";

create table if not exists public.session
(
    session_no smallint,
    id         uuid not null
        primary key,
    module_id  uuid
        constraint fk7vcm9xoec4eqifbkryow1dv1y
            references public.module
);

alter table public.session
    owner to "user";

create table if not exists public.test
(
    date       timestamp(6) not null,
    id         bigserial
        primary key,
    test_no    bigint,
    time       bigint       not null,
    cycle_id   uuid,
    program_id uuid,
    agenda     varchar(255),
    type       varchar(255)
        constraint test_type_check
            check ((type)::text = ANY
                   ((ARRAY ['ESSAY'::character varying, 'MULTIPLE_CHOICE'::character varying])::text[])),
    constraint fkb0bedmglp9mxekokmuqxufabj
        foreign key (cycle_id, program_id) references public.program_per_cycle
);

alter table public.test
    owner to "user";

create table if not exists public.test_question
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

create table if not exists public.trainee
(
    is_deleted   boolean      not null,
    birthdate    timestamp(6),
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    account_id   uuid
        unique
        constraint fkx5v2xc72p5f8jm2trjwswgu
            references public.account,
    created_by   uuid         not null,
    id           uuid         not null
        primary key,
    profile_id   uuid
        unique
        constraint fkltl5tu7qk21at5ctvt7mc6v1c
            references public.profile,
    updated_by   uuid,
    name         varchar(255) not null,
    phone        varchar(255) not null
);

alter table public.trainee
    owner to "user";

create table if not exists public.attendance
(
    is_deleted   boolean      not null,
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    created_by   uuid         not null,
    id           uuid         not null
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

create table if not exists public.enrollment
(
    is_cancelled    boolean      not null,
    enrollment_date timestamp(6) not null,
    cycle_id        uuid         not null
        constraint fk9qhq4kj769exbklnarshma93o
            references public.cycle,
    program_id      uuid         not null
        constraint fksv31c7aw3p6lgvhaulei4jmwt
            references public.program,
    trainee_id      uuid         not null
        constraint fkgbr3ng3rd30tmhyw0gc32oxli
            references public.trainee,
    cancel_reason   varchar(255),
    primary key (cycle_id, program_id, trainee_id)
);

alter table public.enrollment
    owner to "user";

create table if not exists public.test_score
(
    score      double precision not null,
    id         bigserial
        primary key,
    test_id    bigint
        constraint fkpapokkjbrc8brpowyr2u1rpyg
            references public.test,
    cycle_id   uuid,
    trainee_id uuid
        constraint fktpkhxuxe5l12ra7auyxvkjomp
            references public.trainee
);

alter table public.test_score
    owner to "user";

create table if not exists public.trainer
(
    is_deleted   boolean      not null,
    birthdate    timestamp(6),
    created_date timestamp(6) not null,
    updated_date timestamp(6),
    account_id   uuid
        unique
        constraint fkmkspgds6yphi0blxbb7j6smv8
            references public.account,
    created_by   uuid         not null,
    id           uuid         not null
        primary key,
    updated_by   uuid,
    name         varchar(255) not null,
    phone        varchar(255) not null
);

alter table public.trainer
    owner to "user";

create table if not exists public.unit
(
    duration    integer not null,
    id          serial
        primary key,
    unit_no     smallint,
    session_id  uuid
        constraint fk3ltxu5g3m8paauww2nigsjh07
            references public.session,
    description varchar(255),
    name        varchar(255)
);

alter table public.unit
    owner to "user";

