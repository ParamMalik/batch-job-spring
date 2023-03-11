CREATE TABLE bill_table (
    id SERIAL PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    amount NUMERIC(10,2),
    due_date DATE ,
    status VARCHAR(2000)
);
