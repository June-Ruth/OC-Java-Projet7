DELETE FROM test.bid_list;
DELETE FROM test.curve_point;
DELETE FROM test.rating;
DELETE FROM test.rule_name;
DELETE FROM test.trade;
DELETE FROM test.user;

INSERT INTO test.bid_list(bid_list_id, account, type, bid_quantity) VALUES (01, 'Account 1', 'Type 1', 10.00);
INSERT INTO test.bid_list(bid_list_id, account, type, bid_quantity) VALUES (02, 'Account 2', 'Type 2', 20.00);

INSERT INTO test.curve_point(id, curve_id, term, value) VALUES (01, 01, 'Term 1', 01);
INSERT INTO test.curve_point(id, curve_id, term, value) VALUES (02, 02, 'Term 2', 02);

INSERT INTO test.rating(id, moodys_rating, sand_p_rating, fitch_rating, order_number)
VALUES (01, 'Moodys Rating 1', 'Sand P Rating 1', 'Fitch Rating 1', 01);
INSERT INTO test.rating(id, moodys_rating, sand_p_rating, fitch_rating, order_number)
VALUES (02, 'Moodys Rating 2', 'Sand P Rating 2', 'Fitch Rating 2', 02);

INSERT INTO test.rule_name(id, name, description, json, template, sql_str, sql_part)
VALUES (01, 'Name 1', 'Description 1', 'Json 1', 'Template 1', 'SQL str 1', 'SQL part 1');
INSERT INTO test.rule_name(id, name, description, json, template, sql_str, sql_part)
VALUES (02, 'Name 2', 'Description 2', 'Json 2', 'Template 2', 'SQL str 2', 'SQL part 2');

INSERT INTO test.trade(trade_id, account, type, buy_quantity) VALUES (01, 'Account 1', 'Type 1', 10.00);
INSERT INTO test.trade(trade_id, account, type, buy_quantity) VALUES (02, 'Account 2', 'Type 2', 20.00);

INSERT INTO test.user(id, username, password, fullname, role) VALUES (01, 'User', 'password', 'User Test', 'ROLE_USER');
INSERT INTO test.user(id, username, password, fullname, role) VALUES (02, 'Admin', 'password', 'Admin Test', 'ROLE_ADMIN');
