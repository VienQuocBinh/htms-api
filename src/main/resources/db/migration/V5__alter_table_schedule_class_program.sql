alter table schedule
    drop constraint if exists schedule_trainer_id_key;

alter table class
    add column max_quantity integer,
    add column min_quantity integer;


alter table if exists program
    drop column if exists min_quantity,
    drop column if exists max_quantity;
