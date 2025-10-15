CREATE TABLE roles
(
    role_name     VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (role_name)
);

CREATE TABLE roles_permisions
(
    roles_entity_role_name    VARCHAR(255) NOT NULL,
    permisions_permision_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles_permisions PRIMARY KEY (roles_entity_role_name, permisions_permision_name)
);

ALTER TABLE roles_permisions
    ADD CONSTRAINT fk_rolper_on_permisstion_entity FOREIGN KEY (permisions_permision_name) REFERENCES permisstion_entity (permision_name);

ALTER TABLE roles_permisions
    ADD CONSTRAINT fk_rolper_on_roles_entity FOREIGN KEY (roles_entity_role_name) REFERENCES roles (role_name);