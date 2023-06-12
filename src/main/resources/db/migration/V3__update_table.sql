alter table enrollment
    add constraint enrollment_status_check
        check ( (status)::text = ANY
                ((ARRAY ['REJECT'::character varying, 'APPROVE'::character varying, 'PENDING'::character varying])::text[]));

alter table program
    add is_root boolean;
alter table program
    drop constraint program_code_key;