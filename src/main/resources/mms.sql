
    alter table jnyw_mms_bill_detail 
        drop 
        foreign key FKB64B3940E924F583

    alter table jnyw_mms_bill_detail 
        drop 
        foreign key FKB64B3940936F2503

    alter table jnyw_mms_dept 
        drop 
        foreign key FK8EC5606E6B18843E

    alter table jnyw_mms_material 
        drop 
        foreign key FKCCAA8AD029CBAE2

    alter table jnyw_mms_material_class 
        drop 
        foreign key FKE3ED3B49D2FA0E58

    alter table jnyw_mms_material_images 
        drop 
        foreign key FKA4052AC7936F2503

    alter table jnyw_mms_middle_role_privilege 
        drop 
        foreign key FK26C18309A5D72491

    alter table jnyw_mms_middle_role_privilege 
        drop 
        foreign key FK26C1830941C9EB23

    alter table jnyw_mms_middle_user_role 
        drop 
        foreign key FKBDA3FEE941C9EB23

    alter table jnyw_mms_middle_user_role 
        drop 
        foreign key FKBDA3FEE9E6F4AF03

    alter table jnyw_mms_privilege 
        drop 
        foreign key FK42088628EEC25F34

    alter table jnyw_mms_stock 
        drop 
        foreign key FK4AC0DA0D936F2503

    alter table jnyw_mms_user 
        drop 
        foreign key FK8ECD4DF44C5AE4C3

    drop table if exists jnyw_mms_bill

    drop table if exists jnyw_mms_bill_detail

    drop table if exists jnyw_mms_depot

    drop table if exists jnyw_mms_dept

    drop table if exists jnyw_mms_material

    drop table if exists jnyw_mms_material_class

    drop table if exists jnyw_mms_material_images

    drop table if exists jnyw_mms_measureunit

    drop table if exists jnyw_mms_middle_role_privilege

    drop table if exists jnyw_mms_middle_user_role

    drop table if exists jnyw_mms_person

    drop table if exists jnyw_mms_privilege

    drop table if exists jnyw_mms_privilege_type

    drop table if exists jnyw_mms_role

    drop table if exists jnyw_mms_stock

    drop table if exists jnyw_mms_supplier

    drop table if exists jnyw_mms_user

    drop table if exists mms_oauth2_aut

    create table jnyw_mms_bill (
        id varchar(32) not null,
        billCost double precision,
        billCount integer,
        billDate date,
        billNo varchar(255),
        billType varchar(255),
        deleteFlag integer,
        depot2Id varchar(255),
        depot2Name varchar(255),
        depotId varchar(255),
        depotName varchar(255),
        intercourseId varchar(255),
        intercourseName varchar(255),
        makeBillDate datetime,
        remark varchar(255),
        userId varchar(255),
        primary key (id)
    )

    create table jnyw_mms_bill_detail (
        id varchar(32) not null,
        count integer,
        price double precision,
        bill_id varchar(32),
        material_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_depot (
        id varchar(32) not null,
        depotCode varchar(255),
        depotDirections longtext,
        depotName varchar(255),
        primary key (id)
    )

    create table jnyw_mms_dept (
        id varchar(32) not null,
        deptName varchar(255),
        parent_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_material (
        id varchar(32) not null,
        disinfection varchar(255),
        factory varchar(255),
        highValue varchar(255),
        lowerLimit integer,
        materialBrand varchar(255),
        materialModel varchar(255),
        materialName varchar(255),
        materialNo varchar(255),
        materialSpec varchar(255),
        measureUnitId varchar(255),
        pym varchar(255),
        remark varchar(255),
        sellPrice float,
        upperLimit integer,
        material_class_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_material_class (
        id varchar(32) not null,
        materialClassName varchar(255),
        parent_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_material_images (
        id varchar(32) not null,
        extName varchar(255),
        imageFullName varchar(255),
        imageName varchar(255),
        imageSize varchar(255),
        savePath varchar(255),
        material_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_measureunit (
        id varchar(32) not null,
        code varchar(255),
        description varchar(255),
        unitName varchar(255),
        primary key (id)
    )

    create table jnyw_mms_middle_role_privilege (
        role_id varchar(32) not null,
        privilege_id varchar(32) not null,
        primary key (role_id, privilege_id)
    )

    create table jnyw_mms_middle_user_role (
        user_id varchar(32) not null,
        role_id varchar(32) not null,
        primary key (user_id, role_id)
    )

    create table jnyw_mms_person (
        id varchar(32) not null,
        person_name varchar(255),
        person_sex varchar(255),
        primary key (id)
    )

    create table jnyw_mms_privilege (
        id varchar(32) not null,
        description varchar(255),
        privilegeName varchar(255),
        sort integer,
        url varchar(255),
        privilege_type_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_privilege_type (
        id varchar(32) not null,
        description varchar(255),
        privilegeTypeName varchar(255),
        sort integer,
        primary key (id)
    )

    create table jnyw_mms_role (
        id varchar(32) not null,
        description varchar(255),
        roleName varchar(255),
        sort integer,
        primary key (id)
    )

    create table jnyw_mms_stock (
        id varchar(32) not null,
        count integer,
        depotId varchar(255),
        inPrice double precision,
        totalPrice double precision,
        material_id varchar(32),
        primary key (id)
    )

    create table jnyw_mms_supplier (
        id varchar(32) not null,
        account varchar(255),
        addr varchar(255),
        answerMan varchar(255),
        bank varchar(255),
        code varchar(255),
        contactMan varchar(255),
        email varchar(255),
        fax varchar(255),
        fullName varchar(255),
        help varchar(255),
        leibie varchar(255),
        licence varchar(255),
        phone varchar(255),
        postcode varchar(255),
        remark varchar(255),
        shortName varchar(255),
        taxCode varchar(255),
        www varchar(255),
        primary key (id)
    )

    create table jnyw_mms_user (
        id varchar(32) not null,
        email varchar(255),
        inTime date,
        loginName varchar(255),
        outTime date,
        password varchar(255),
        phoneNum varchar(255),
        sex varchar(255),
        userName varchar(255),
        dept_id varchar(32),
        primary key (id)
    )

    create table mms_oauth2_aut (
        id varchar(32) not null,
        accessToken varchar(255),
        asAuthorized smallint,
        atExpirationTime datetime,
        autCode varchar(255),
        clientDomain varchar(255),
        clientId varchar(255),
        clientName varchar(255),
        clientSecret varchar(255),
        grantType varchar(255),
        resourceOwner varchar(255),
        roAuthorized smallint,
        primary key (id)
    )

    alter table jnyw_mms_bill_detail 
        add index FKB64B3940E924F583 (bill_id), 
        add constraint FKB64B3940E924F583 
        foreign key (bill_id) 
        references jnyw_mms_bill (id)

    alter table jnyw_mms_bill_detail 
        add index FKB64B3940936F2503 (material_id), 
        add constraint FKB64B3940936F2503 
        foreign key (material_id) 
        references jnyw_mms_material (id)

    alter table jnyw_mms_dept 
        add index FK8EC5606E6B18843E (parent_id), 
        add constraint FK8EC5606E6B18843E 
        foreign key (parent_id) 
        references jnyw_mms_dept (id)

    alter table jnyw_mms_material 
        add index FKCCAA8AD029CBAE2 (material_class_id), 
        add constraint FKCCAA8AD029CBAE2 
        foreign key (material_class_id) 
        references jnyw_mms_material_class (id)

    alter table jnyw_mms_material_class 
        add index FKE3ED3B49D2FA0E58 (parent_id), 
        add constraint FKE3ED3B49D2FA0E58 
        foreign key (parent_id) 
        references jnyw_mms_material_class (id)

    alter table jnyw_mms_material_images 
        add index FKA4052AC7936F2503 (material_id), 
        add constraint FKA4052AC7936F2503 
        foreign key (material_id) 
        references jnyw_mms_material (id)

    alter table jnyw_mms_middle_role_privilege 
        add index FK26C18309A5D72491 (privilege_id), 
        add constraint FK26C18309A5D72491 
        foreign key (privilege_id) 
        references jnyw_mms_privilege (id)

    alter table jnyw_mms_middle_role_privilege 
        add index FK26C1830941C9EB23 (role_id), 
        add constraint FK26C1830941C9EB23 
        foreign key (role_id) 
        references jnyw_mms_role (id)

    alter table jnyw_mms_middle_user_role 
        add index FKBDA3FEE941C9EB23 (role_id), 
        add constraint FKBDA3FEE941C9EB23 
        foreign key (role_id) 
        references jnyw_mms_role (id)

    alter table jnyw_mms_middle_user_role 
        add index FKBDA3FEE9E6F4AF03 (user_id), 
        add constraint FKBDA3FEE9E6F4AF03 
        foreign key (user_id) 
        references jnyw_mms_user (id)

    alter table jnyw_mms_privilege 
        add index FK42088628EEC25F34 (privilege_type_id), 
        add constraint FK42088628EEC25F34 
        foreign key (privilege_type_id) 
        references jnyw_mms_privilege_type (id)

    alter table jnyw_mms_stock 
        add index FK4AC0DA0D936F2503 (material_id), 
        add constraint FK4AC0DA0D936F2503 
        foreign key (material_id) 
        references jnyw_mms_material (id)

    alter table jnyw_mms_user 
        add index FK8ECD4DF44C5AE4C3 (dept_id), 
        add constraint FK8ECD4DF44C5AE4C3 
        foreign key (dept_id) 
        references jnyw_mms_dept (id)
