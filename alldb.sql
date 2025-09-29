--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:0F/A428gw17seEbdqXTlOg==$c32tb9a6mJVHi5nPYWmPOgg/I96SL22oMFOCIX0loMw=:0f5bMQyEbDV11R/y7W25zFOBFQrCNmXf0xvRchArhds=';

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.13
-- Dumped by pg_dump version 15.13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

--
-- Database "account_database" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.13
-- Dumped by pg_dump version 15.13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: account_database; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE account_database WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE account_database OWNER TO postgres;

\connect account_database

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    birth_date date,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    id uuid NOT NULL,
    city character varying(255),
    created_by character varying(255),
    email character varying(255),
    emergency_contact_first_name character varying(255),
    emergency_contact_last_name character varying(255),
    emergency_contact_phone_number character varying(255),
    emergency_contact_relationship character varying(255),
    first_name character varying(255),
    gender character varying(255),
    image_url character varying(255),
    last_name character varying(255),
    middle_name character varying(255),
    modified_by character varying(255),
    name_suffix character varying(255),
    phone_number character varying(255),
    state character varying(255),
    status character varying(255),
    street character varying(255),
    zip_code character varying(255),
    CONSTRAINT accounts_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying, 'SUSPENDED'::character varying, 'DELETED'::character varying])::text[])))
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (birth_date, created_at, updated_at, id, city, created_by, email, emergency_contact_first_name, emergency_contact_last_name, emergency_contact_phone_number, emergency_contact_relationship, first_name, gender, image_url, last_name, middle_name, modified_by, name_suffix, phone_number, state, status, street, zip_code) FROM stdin;
1990-05-20	2025-09-14 20:09:11.001425	2025-09-14 20:09:11.001425	ae32774b-1bed-4ab5-afa0-1ee0295fb9eb	New York	cfd033b1-890f-4a2e-932f-b77f3400ec96	john.doe@example.com	Jane	Doe	+1987654321	Spouse	John	Male	https://example.com/profile.jpg	Doe	Michael	cfd033b1-890f-4a2e-932f-b77f3400ec96	Jr.	+1234567890	NY	ACTIVE	123 Main St	10001
\.


--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- Database "keycloak" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.13
-- Dumped by pg_dump version 15.13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: keycloak; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE keycloak WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE keycloak OWNER TO postgres;

\connect keycloak

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_event_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64),
    details_json text
);


ALTER TABLE public.admin_event_entity OWNER TO postgres;

--
-- Name: associated_policy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE public.associated_policy OWNER TO postgres;

--
-- Name: authentication_execution; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE public.authentication_execution OWNER TO postgres;

--
-- Name: authentication_flow; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE public.authentication_flow OWNER TO postgres;

--
-- Name: authenticator_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.authenticator_config OWNER TO postgres;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authenticator_config_entry OWNER TO postgres;

--
-- Name: broker_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.broker_link OWNER TO postgres;

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client OWNER TO postgres;

--
-- Name: client_attributes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.client_attributes OWNER TO postgres;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE public.client_auth_flow_bindings OWNER TO postgres;

--
-- Name: client_initial_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE public.client_initial_access OWNER TO postgres;

--
-- Name: client_node_registrations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_node_registrations OWNER TO postgres;

--
-- Name: client_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE public.client_scope OWNER TO postgres;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_scope_attributes OWNER TO postgres;

--
-- Name: client_scope_client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client_scope_client OWNER TO postgres;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.client_scope_role_mapping OWNER TO postgres;

--
-- Name: component; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE public.component OWNER TO postgres;

--
-- Name: component_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.component_config OWNER TO postgres;

--
-- Name: composite_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE public.composite_role OWNER TO postgres;

--
-- Name: credential; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer,
    version integer DEFAULT 0
);


ALTER TABLE public.credential OWNER TO postgres;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO postgres;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- Name: default_client_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.default_client_scope OWNER TO postgres;

--
-- Name: event_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255),
    details_json_long_value text
);


ALTER TABLE public.event_entity OWNER TO postgres;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024),
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.fed_user_attribute OWNER TO postgres;

--
-- Name: fed_user_consent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.fed_user_consent OWNER TO postgres;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.fed_user_consent_cl_scope OWNER TO postgres;

--
-- Name: fed_user_credential; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.fed_user_credential OWNER TO postgres;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_group_membership OWNER TO postgres;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_required_action OWNER TO postgres;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_role_mapping OWNER TO postgres;

--
-- Name: federated_identity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_identity OWNER TO postgres;

--
-- Name: federated_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_user OWNER TO postgres;

--
-- Name: group_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_attribute OWNER TO postgres;

--
-- Name: group_role_mapping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_role_mapping OWNER TO postgres;

--
-- Name: identity_provider; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL,
    organization_id character varying(255),
    hide_on_login boolean DEFAULT false
);


ALTER TABLE public.identity_provider OWNER TO postgres;

--
-- Name: identity_provider_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.identity_provider_config OWNER TO postgres;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.identity_provider_mapper OWNER TO postgres;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.idp_mapper_config OWNER TO postgres;

--
-- Name: jgroups_ping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jgroups_ping (
    address character varying(200) NOT NULL,
    name character varying(200),
    cluster_name character varying(200) NOT NULL,
    ip character varying(200) NOT NULL,
    coord boolean
);


ALTER TABLE public.jgroups_ping OWNER TO postgres;

--
-- Name: keycloak_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36),
    type integer DEFAULT 0 NOT NULL,
    description character varying(255)
);


ALTER TABLE public.keycloak_group OWNER TO postgres;

--
-- Name: keycloak_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE public.keycloak_role OWNER TO postgres;

--
-- Name: migration_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.migration_model OWNER TO postgres;

--
-- Name: offline_client_session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.offline_client_session OWNER TO postgres;

--
-- Name: offline_user_session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL,
    broker_session_id character varying(1024),
    version integer DEFAULT 0
);


ALTER TABLE public.offline_user_session OWNER TO postgres;

--
-- Name: org; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.org (
    id character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    realm_id character varying(255) NOT NULL,
    group_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(4000),
    alias character varying(255) NOT NULL,
    redirect_url character varying(2048)
);


ALTER TABLE public.org OWNER TO postgres;

--
-- Name: org_domain; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.org_domain (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    verified boolean NOT NULL,
    org_id character varying(255) NOT NULL
);


ALTER TABLE public.org_domain OWNER TO postgres;

--
-- Name: policy_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.policy_config OWNER TO postgres;

--
-- Name: protocol_mapper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE public.protocol_mapper OWNER TO postgres;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.protocol_mapper_config OWNER TO postgres;

--
-- Name: realm; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE public.realm OWNER TO postgres;

--
-- Name: realm_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE public.realm_attribute OWNER TO postgres;

--
-- Name: realm_default_groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_default_groups OWNER TO postgres;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_enabled_event_types OWNER TO postgres;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_events_listeners OWNER TO postgres;

--
-- Name: realm_localizations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE public.realm_localizations OWNER TO postgres;

--
-- Name: realm_required_credential; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_required_credential OWNER TO postgres;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.realm_smtp_config OWNER TO postgres;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_supported_locales OWNER TO postgres;

--
-- Name: redirect_uris; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.redirect_uris OWNER TO postgres;

--
-- Name: required_action_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.required_action_config OWNER TO postgres;

--
-- Name: required_action_provider; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE public.required_action_provider OWNER TO postgres;

--
-- Name: resource_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_attribute OWNER TO postgres;

--
-- Name: resource_policy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_policy OWNER TO postgres;

--
-- Name: resource_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_scope OWNER TO postgres;

--
-- Name: resource_server; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.resource_server OWNER TO postgres;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE public.resource_server_perm_ticket OWNER TO postgres;

--
-- Name: resource_server_policy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE public.resource_server_policy OWNER TO postgres;

--
-- Name: resource_server_resource; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_resource OWNER TO postgres;

--
-- Name: resource_server_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_scope OWNER TO postgres;

--
-- Name: resource_uris; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.resource_uris OWNER TO postgres;

--
-- Name: revoked_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.revoked_token (
    id character varying(255) NOT NULL,
    expire bigint NOT NULL
);


ALTER TABLE public.revoked_token OWNER TO postgres;

--
-- Name: role_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.role_attribute OWNER TO postgres;

--
-- Name: scope_mapping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_mapping OWNER TO postgres;

--
-- Name: scope_policy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_policy OWNER TO postgres;

--
-- Name: server_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.server_config (
    server_config_key character varying(255) NOT NULL,
    value text NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.server_config OWNER TO postgres;

--
-- Name: user_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.user_attribute OWNER TO postgres;

--
-- Name: user_consent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.user_consent OWNER TO postgres;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.user_consent_client_scope OWNER TO postgres;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_entity OWNER TO postgres;

--
-- Name: user_federation_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_config OWNER TO postgres;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.user_federation_mapper OWNER TO postgres;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_mapper_config OWNER TO postgres;

--
-- Name: user_federation_provider; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.user_federation_provider OWNER TO postgres;

--
-- Name: user_group_membership; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL,
    membership_type character varying(255) NOT NULL
);


ALTER TABLE public.user_group_membership OWNER TO postgres;

--
-- Name: user_required_action; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE public.user_required_action OWNER TO postgres;

--
-- Name: user_role_mapping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_role_mapping OWNER TO postgres;

--
-- Name: web_origins; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.web_origins OWNER TO postgres;

--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type, details_json) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
76eb7495-833b-4848-88f7-e26f1ae816fb	\N	auth-cookie	74fb1520-0e85-4576-bac4-f72784d7f550	f0ff90ae-90c0-4fed-b02e-91c4c5385ce1	2	10	f	\N	\N
86280af1-def7-410b-b500-c114eb6f7afb	\N	auth-spnego	74fb1520-0e85-4576-bac4-f72784d7f550	f0ff90ae-90c0-4fed-b02e-91c4c5385ce1	3	20	f	\N	\N
be274059-9d50-4dc0-971d-0688fc41a5e8	\N	identity-provider-redirector	74fb1520-0e85-4576-bac4-f72784d7f550	f0ff90ae-90c0-4fed-b02e-91c4c5385ce1	2	25	f	\N	\N
2992c015-b8b9-49e9-aede-b0e2110b1993	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	f0ff90ae-90c0-4fed-b02e-91c4c5385ce1	2	30	t	eb3498d9-9dfa-4281-adc4-740849c1c7b2	\N
9a142d58-3e88-4b64-8b41-6625d9ed6d26	\N	auth-username-password-form	74fb1520-0e85-4576-bac4-f72784d7f550	eb3498d9-9dfa-4281-adc4-740849c1c7b2	0	10	f	\N	\N
649a3749-a0ea-4720-95b2-a96223bb0659	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	eb3498d9-9dfa-4281-adc4-740849c1c7b2	1	20	t	efeeb53e-23dc-45e9-a380-bc3eebb0ba12	\N
f46b4ade-c176-494a-bb51-c1bc4e4cece7	\N	conditional-user-configured	74fb1520-0e85-4576-bac4-f72784d7f550	efeeb53e-23dc-45e9-a380-bc3eebb0ba12	0	10	f	\N	\N
ce8f041a-6aa4-4342-adaa-9e5bad201aff	\N	auth-otp-form	74fb1520-0e85-4576-bac4-f72784d7f550	efeeb53e-23dc-45e9-a380-bc3eebb0ba12	2	20	f	\N	\N
2a87c9dd-9ba4-47d7-ab07-b35e55d62f1a	\N	webauthn-authenticator	74fb1520-0e85-4576-bac4-f72784d7f550	efeeb53e-23dc-45e9-a380-bc3eebb0ba12	3	30	f	\N	\N
2d034d15-e5c7-4c19-aa49-d08c27a26a62	\N	auth-recovery-authn-code-form	74fb1520-0e85-4576-bac4-f72784d7f550	efeeb53e-23dc-45e9-a380-bc3eebb0ba12	3	40	f	\N	\N
d3ebdff9-1e9a-47e0-9efb-de9ea28d2be9	\N	direct-grant-validate-username	74fb1520-0e85-4576-bac4-f72784d7f550	b1a1a1e0-2acd-475d-9623-6044b79d1659	0	10	f	\N	\N
c84bb5f8-408e-4e6d-a5bf-90ebf6483a2d	\N	direct-grant-validate-password	74fb1520-0e85-4576-bac4-f72784d7f550	b1a1a1e0-2acd-475d-9623-6044b79d1659	0	20	f	\N	\N
ee76c47c-01a1-4df8-9368-2922ddd193a9	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	b1a1a1e0-2acd-475d-9623-6044b79d1659	1	30	t	108085fb-a6e4-47cf-8524-8fd8b0ca8635	\N
3a599556-7075-4e49-8d11-026b862dd15c	\N	conditional-user-configured	74fb1520-0e85-4576-bac4-f72784d7f550	108085fb-a6e4-47cf-8524-8fd8b0ca8635	0	10	f	\N	\N
898e7e2b-8e5c-467b-97ba-db5ef2ebe0d2	\N	direct-grant-validate-otp	74fb1520-0e85-4576-bac4-f72784d7f550	108085fb-a6e4-47cf-8524-8fd8b0ca8635	0	20	f	\N	\N
17ac3670-c446-4d70-ae26-a27f5f674437	\N	registration-page-form	74fb1520-0e85-4576-bac4-f72784d7f550	573bef42-1473-4cd5-8e68-f45c9b9d64b1	0	10	t	79dc5e21-504d-4e5a-af5a-cf1d9b24a9d1	\N
36ab814b-a51c-424f-94a2-7a20f3db9668	\N	registration-user-creation	74fb1520-0e85-4576-bac4-f72784d7f550	79dc5e21-504d-4e5a-af5a-cf1d9b24a9d1	0	20	f	\N	\N
0498e81c-35df-48a3-9e56-4b8185186456	\N	registration-password-action	74fb1520-0e85-4576-bac4-f72784d7f550	79dc5e21-504d-4e5a-af5a-cf1d9b24a9d1	0	50	f	\N	\N
99f530ce-0ccc-431f-a009-4bbd34be460c	\N	registration-recaptcha-action	74fb1520-0e85-4576-bac4-f72784d7f550	79dc5e21-504d-4e5a-af5a-cf1d9b24a9d1	3	60	f	\N	\N
2fdcbb69-7216-4e34-8311-c278f12bc03c	\N	registration-terms-and-conditions	74fb1520-0e85-4576-bac4-f72784d7f550	79dc5e21-504d-4e5a-af5a-cf1d9b24a9d1	3	70	f	\N	\N
6fee7e70-61ef-4bd0-9320-8c4090aa08b0	\N	reset-credentials-choose-user	74fb1520-0e85-4576-bac4-f72784d7f550	f596a2e9-bcb8-472d-959c-ee2cc3076e11	0	10	f	\N	\N
322630a9-6f1f-4b29-bcde-8fe42f247822	\N	reset-credential-email	74fb1520-0e85-4576-bac4-f72784d7f550	f596a2e9-bcb8-472d-959c-ee2cc3076e11	0	20	f	\N	\N
f5eb82f1-e550-4d76-9c8d-b4c155eb5671	\N	reset-password	74fb1520-0e85-4576-bac4-f72784d7f550	f596a2e9-bcb8-472d-959c-ee2cc3076e11	0	30	f	\N	\N
60b225ca-f2ca-418d-a35d-0cfaa17182ef	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	f596a2e9-bcb8-472d-959c-ee2cc3076e11	1	40	t	7972ba4c-0774-41d3-8404-99e6ad1f6425	\N
bb687ea3-f90f-4cb6-ae07-7a19ce9f2399	\N	conditional-user-configured	74fb1520-0e85-4576-bac4-f72784d7f550	7972ba4c-0774-41d3-8404-99e6ad1f6425	0	10	f	\N	\N
0174397b-ec8b-4868-978b-6bca2d273ebc	\N	reset-otp	74fb1520-0e85-4576-bac4-f72784d7f550	7972ba4c-0774-41d3-8404-99e6ad1f6425	0	20	f	\N	\N
df04c9d5-32a3-49fb-8f55-7ee16885ca89	\N	client-secret	74fb1520-0e85-4576-bac4-f72784d7f550	95c24d13-78ca-4699-a279-e28110af37d5	2	10	f	\N	\N
52376c8b-b834-47db-9265-bcab9529e772	\N	client-jwt	74fb1520-0e85-4576-bac4-f72784d7f550	95c24d13-78ca-4699-a279-e28110af37d5	2	20	f	\N	\N
c1a668aa-6c4b-45ee-b764-7bec1d1de064	\N	client-secret-jwt	74fb1520-0e85-4576-bac4-f72784d7f550	95c24d13-78ca-4699-a279-e28110af37d5	2	30	f	\N	\N
3dbb931e-469d-4758-b06e-5f0f3b6659fb	\N	client-x509	74fb1520-0e85-4576-bac4-f72784d7f550	95c24d13-78ca-4699-a279-e28110af37d5	2	40	f	\N	\N
bf7a6872-3aa0-4b14-a0c2-93c362e79118	\N	idp-review-profile	74fb1520-0e85-4576-bac4-f72784d7f550	9bba66b1-bc34-4dcf-ab42-e4eba70a85c7	0	10	f	\N	c580b132-98ed-456b-8915-f93e00b758e0
991686c5-d958-4e81-b609-06c78e05b1b3	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	9bba66b1-bc34-4dcf-ab42-e4eba70a85c7	0	20	t	e9efbe13-638e-4d35-b56a-7b2f32c91a04	\N
e0b11738-190f-4a0e-8f82-c21aa70c188c	\N	idp-create-user-if-unique	74fb1520-0e85-4576-bac4-f72784d7f550	e9efbe13-638e-4d35-b56a-7b2f32c91a04	2	10	f	\N	efd9a0d5-708b-482d-b6e8-e49b0b719e31
b0834a7e-9603-4304-b345-98161b0a9788	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	e9efbe13-638e-4d35-b56a-7b2f32c91a04	2	20	t	9822beac-e7f1-4c46-b520-ac7ceb15fc6b	\N
1665c1b5-a11f-4949-86c7-5a4b28908d4f	\N	idp-confirm-link	74fb1520-0e85-4576-bac4-f72784d7f550	9822beac-e7f1-4c46-b520-ac7ceb15fc6b	0	10	f	\N	\N
f5545755-e470-413c-8998-db890a7877cc	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	9822beac-e7f1-4c46-b520-ac7ceb15fc6b	0	20	t	0cccd315-e8f8-43c6-9338-e9a8e6510f5b	\N
2bdbe7c4-1e82-46cc-8871-b57385a7e1b2	\N	idp-email-verification	74fb1520-0e85-4576-bac4-f72784d7f550	0cccd315-e8f8-43c6-9338-e9a8e6510f5b	2	10	f	\N	\N
7a39040c-6571-44b7-8c90-194521d3e517	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	0cccd315-e8f8-43c6-9338-e9a8e6510f5b	2	20	t	94bc5195-f1b5-4257-b794-b7c0401f5693	\N
3ed8079f-4b92-406f-85a5-a4012e87ee6d	\N	idp-username-password-form	74fb1520-0e85-4576-bac4-f72784d7f550	94bc5195-f1b5-4257-b794-b7c0401f5693	0	10	f	\N	\N
70bf82f7-2a84-41dd-bc15-16bb8f9a689b	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	94bc5195-f1b5-4257-b794-b7c0401f5693	1	20	t	0b776f91-ba6c-49a9-952e-364d754fdf36	\N
3219859b-0f0d-404b-83c5-6fb5ed52b978	\N	conditional-user-configured	74fb1520-0e85-4576-bac4-f72784d7f550	0b776f91-ba6c-49a9-952e-364d754fdf36	0	10	f	\N	\N
c8b5e312-6594-4cd2-ba14-25c3fe0ac829	\N	auth-otp-form	74fb1520-0e85-4576-bac4-f72784d7f550	0b776f91-ba6c-49a9-952e-364d754fdf36	2	20	f	\N	\N
9837dc11-60e4-46c7-9915-cf009bbc8c8b	\N	webauthn-authenticator	74fb1520-0e85-4576-bac4-f72784d7f550	0b776f91-ba6c-49a9-952e-364d754fdf36	3	30	f	\N	\N
3aec5d47-a54e-4076-8766-5cb1709ca6a3	\N	auth-recovery-authn-code-form	74fb1520-0e85-4576-bac4-f72784d7f550	0b776f91-ba6c-49a9-952e-364d754fdf36	3	40	f	\N	\N
f5e80868-3e0c-40be-99ce-0955fd9f930e	\N	http-basic-authenticator	74fb1520-0e85-4576-bac4-f72784d7f550	24ef9b2e-0cd2-4bba-966f-f0b50b9fde21	0	10	f	\N	\N
fbf0fb05-fa9c-4397-8219-aa2f7baecabd	\N	docker-http-basic-authenticator	74fb1520-0e85-4576-bac4-f72784d7f550	d517c279-1a13-45eb-903c-20d31f6e60d9	0	10	f	\N	\N
9f4afc03-f8e7-409c-9dc9-12522be10c9f	\N	auth-cookie	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	2	10	f	\N	\N
8b8ecaa1-e5b9-4bbb-8134-d502773349ce	\N	auth-spnego	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	3	20	f	\N	\N
e0fc68bc-f555-495c-9378-62c1721e3b24	\N	identity-provider-redirector	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	2	25	f	\N	\N
e0c064ba-951b-40fe-a274-eee5b98422b0	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	2	30	t	3721e5c9-b7b3-4e3e-a1a6-d93de9803081	\N
3561526e-d2e4-49ec-9912-0c9791b606ce	\N	auth-username-password-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3721e5c9-b7b3-4e3e-a1a6-d93de9803081	0	10	f	\N	\N
610dfad7-4cc8-4b2a-8734-47c39f001dde	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3721e5c9-b7b3-4e3e-a1a6-d93de9803081	1	20	t	4c268c94-8bb4-4671-9961-599feabfb66d	\N
25430d23-e542-4fa5-bd7b-f82777136011	\N	conditional-user-configured	c09c14f3-a5f7-4baa-be03-70f28dad6f95	4c268c94-8bb4-4671-9961-599feabfb66d	0	10	f	\N	\N
9cd264f0-c513-42ec-966e-b926a29eced4	\N	auth-otp-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	4c268c94-8bb4-4671-9961-599feabfb66d	2	20	f	\N	\N
39084645-7683-4dcd-a471-10125a15e5b7	\N	webauthn-authenticator	c09c14f3-a5f7-4baa-be03-70f28dad6f95	4c268c94-8bb4-4671-9961-599feabfb66d	3	30	f	\N	\N
cc1a88b4-389b-49ce-9c27-67cc912adb0c	\N	auth-recovery-authn-code-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	4c268c94-8bb4-4671-9961-599feabfb66d	3	40	f	\N	\N
4d52c099-6d4b-49bb-be01-76e152254f02	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	2	26	t	1d5ab553-543b-43a1-9488-4582d59968fd	\N
81a73ae7-7182-435f-a1c4-f3481dfc7265	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	1d5ab553-543b-43a1-9488-4582d59968fd	1	10	t	47c55654-4810-4e60-a925-b80d579b0795	\N
e10c86fb-3c24-463e-b4e8-efc5f914db62	\N	conditional-user-configured	c09c14f3-a5f7-4baa-be03-70f28dad6f95	47c55654-4810-4e60-a925-b80d579b0795	0	10	f	\N	\N
eb3151f3-9142-4dbf-9023-784f306a6815	\N	organization	c09c14f3-a5f7-4baa-be03-70f28dad6f95	47c55654-4810-4e60-a925-b80d579b0795	2	20	f	\N	\N
6f04109c-55c5-4363-8969-e61dbb2248e7	\N	direct-grant-validate-username	c09c14f3-a5f7-4baa-be03-70f28dad6f95	11db3f92-8c30-49e2-8ac1-8adb23bf5027	0	10	f	\N	\N
22601589-9648-40e6-a280-7860a699806c	\N	direct-grant-validate-password	c09c14f3-a5f7-4baa-be03-70f28dad6f95	11db3f92-8c30-49e2-8ac1-8adb23bf5027	0	20	f	\N	\N
5e2ccc91-123b-41bf-8d9f-4436a0f763ae	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	11db3f92-8c30-49e2-8ac1-8adb23bf5027	1	30	t	55d1c76f-b2fe-4f95-b88d-04812faac685	\N
2cbce056-f39a-4009-9c89-10226211cbbf	\N	conditional-user-configured	c09c14f3-a5f7-4baa-be03-70f28dad6f95	55d1c76f-b2fe-4f95-b88d-04812faac685	0	10	f	\N	\N
c0792c9a-e6fb-49ff-9ae1-a215253db764	\N	direct-grant-validate-otp	c09c14f3-a5f7-4baa-be03-70f28dad6f95	55d1c76f-b2fe-4f95-b88d-04812faac685	0	20	f	\N	\N
8ae2ed97-9f55-437f-a327-d8cbf5a12669	\N	registration-page-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	96906da9-abfc-445c-8e78-476e6c133599	0	10	t	ed9a67da-62e1-42c4-9026-7e1a02190544	\N
0393a7d0-442e-46b7-af07-0a5182a78d84	\N	registration-user-creation	c09c14f3-a5f7-4baa-be03-70f28dad6f95	ed9a67da-62e1-42c4-9026-7e1a02190544	0	20	f	\N	\N
5099f8e4-61ac-4430-9cd8-6f54f447b7fa	\N	registration-password-action	c09c14f3-a5f7-4baa-be03-70f28dad6f95	ed9a67da-62e1-42c4-9026-7e1a02190544	0	50	f	\N	\N
e936ae53-3945-4e1a-bcae-344291553524	\N	registration-recaptcha-action	c09c14f3-a5f7-4baa-be03-70f28dad6f95	ed9a67da-62e1-42c4-9026-7e1a02190544	3	60	f	\N	\N
ca9c846b-c7ec-4402-b51e-1bd53b624206	\N	registration-terms-and-conditions	c09c14f3-a5f7-4baa-be03-70f28dad6f95	ed9a67da-62e1-42c4-9026-7e1a02190544	3	70	f	\N	\N
840f5a74-cc06-4429-a91c-67f108030321	\N	reset-credentials-choose-user	c09c14f3-a5f7-4baa-be03-70f28dad6f95	cd630879-8659-4c60-a8be-95c35c372224	0	10	f	\N	\N
57af7db1-98b7-43d2-9003-021d4f8c188d	\N	reset-credential-email	c09c14f3-a5f7-4baa-be03-70f28dad6f95	cd630879-8659-4c60-a8be-95c35c372224	0	20	f	\N	\N
531234ae-cc0b-40fe-8b96-6577b6766ef0	\N	reset-password	c09c14f3-a5f7-4baa-be03-70f28dad6f95	cd630879-8659-4c60-a8be-95c35c372224	0	30	f	\N	\N
59c724f0-bbd5-4b90-ab76-8e4f638db0bd	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	cd630879-8659-4c60-a8be-95c35c372224	1	40	t	13e8aa40-02e8-4612-b946-7c8985bbf3c2	\N
3fbd9e1d-4fa0-4a72-a8d9-38927dd2efd2	\N	conditional-user-configured	c09c14f3-a5f7-4baa-be03-70f28dad6f95	13e8aa40-02e8-4612-b946-7c8985bbf3c2	0	10	f	\N	\N
4c6658be-62d5-4c34-8072-080e936bc15e	\N	reset-otp	c09c14f3-a5f7-4baa-be03-70f28dad6f95	13e8aa40-02e8-4612-b946-7c8985bbf3c2	0	20	f	\N	\N
6f58523e-2ca3-4f30-a39c-f9c1f120f6c4	\N	client-secret	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f206b76a-9b18-4776-ace9-7a8803097fbe	2	10	f	\N	\N
f67f6b79-2df4-4a4c-b65f-5448d09b5d54	\N	client-jwt	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f206b76a-9b18-4776-ace9-7a8803097fbe	2	20	f	\N	\N
139e07b6-04be-4db6-a7f2-4977efdced0e	\N	client-secret-jwt	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f206b76a-9b18-4776-ace9-7a8803097fbe	2	30	f	\N	\N
5701ea21-588d-4900-b73b-a6adb58e0904	\N	client-x509	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f206b76a-9b18-4776-ace9-7a8803097fbe	2	40	f	\N	\N
2ba260fd-fcf9-45aa-8d5e-917c8592a64f	\N	idp-review-profile	c09c14f3-a5f7-4baa-be03-70f28dad6f95	6eb93342-0143-4a21-b1c2-c2e4598585c5	0	10	f	\N	5507efee-53be-4106-a40c-0925395e72cf
8d09ffaa-33dd-42e6-bef5-6cb577c71a84	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	6eb93342-0143-4a21-b1c2-c2e4598585c5	0	20	t	e3914d2f-41f6-4687-a7eb-3366e48f32aa	\N
fe383065-f125-4c37-aefa-fd343e38dffc	\N	idp-create-user-if-unique	c09c14f3-a5f7-4baa-be03-70f28dad6f95	e3914d2f-41f6-4687-a7eb-3366e48f32aa	2	10	f	\N	b6c3b799-af9c-4798-a126-120a76a7fed2
9f979702-3d87-4ae0-8633-be8c8f7a77ce	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	e3914d2f-41f6-4687-a7eb-3366e48f32aa	2	20	t	8678f91a-7a27-4ef3-b706-5109aa3e8536	\N
eba13e54-836f-4bdb-a425-15337383df39	\N	idp-confirm-link	c09c14f3-a5f7-4baa-be03-70f28dad6f95	8678f91a-7a27-4ef3-b706-5109aa3e8536	0	10	f	\N	\N
d16ce739-da19-480d-a8af-ff19b45314df	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	8678f91a-7a27-4ef3-b706-5109aa3e8536	0	20	t	d19841e2-bce5-42e8-b144-255683ef412a	\N
0dedf260-70f5-47b0-8d33-088c4cb9377a	\N	idp-email-verification	c09c14f3-a5f7-4baa-be03-70f28dad6f95	d19841e2-bce5-42e8-b144-255683ef412a	2	10	f	\N	\N
44acf940-e9b0-4f20-afed-69b6f62a099d	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	d19841e2-bce5-42e8-b144-255683ef412a	2	20	t	a498df84-187e-4669-8f4a-5603afee267b	\N
d5e24a64-ca0a-43c7-96d5-c87a60721f46	\N	idp-username-password-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	a498df84-187e-4669-8f4a-5603afee267b	0	10	f	\N	\N
75500ba2-0e28-47fd-b01c-7561f0379735	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	a498df84-187e-4669-8f4a-5603afee267b	1	20	t	86aba2f0-4369-4837-9924-4b5a45ed2a67	\N
a4ad9e10-78e7-45ec-8600-0ceba8412f46	\N	conditional-user-configured	c09c14f3-a5f7-4baa-be03-70f28dad6f95	86aba2f0-4369-4837-9924-4b5a45ed2a67	0	10	f	\N	\N
fa2eb801-9ea1-4fc8-89f6-23b066d98c56	\N	auth-otp-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	86aba2f0-4369-4837-9924-4b5a45ed2a67	2	20	f	\N	\N
4bb6741b-b42c-4dd7-8067-b32e6040fe1b	\N	webauthn-authenticator	c09c14f3-a5f7-4baa-be03-70f28dad6f95	86aba2f0-4369-4837-9924-4b5a45ed2a67	3	30	f	\N	\N
136b4a31-33dd-4332-bc67-271d148386b9	\N	auth-recovery-authn-code-form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	86aba2f0-4369-4837-9924-4b5a45ed2a67	3	40	f	\N	\N
6cf291fa-7d66-43dd-92cf-7b45d14c049d	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	6eb93342-0143-4a21-b1c2-c2e4598585c5	1	50	t	eb624147-2b91-45ca-bc32-aee89db84566	\N
fdae3d67-c415-49e3-ad0b-7b982e7134a9	\N	conditional-user-configured	c09c14f3-a5f7-4baa-be03-70f28dad6f95	eb624147-2b91-45ca-bc32-aee89db84566	0	10	f	\N	\N
88f2b919-9c68-4ed5-b9f2-02051ad3ed6e	\N	idp-add-organization-member	c09c14f3-a5f7-4baa-be03-70f28dad6f95	eb624147-2b91-45ca-bc32-aee89db84566	0	20	f	\N	\N
771879f7-7721-4b2e-9626-4b6d34bdb93c	\N	http-basic-authenticator	c09c14f3-a5f7-4baa-be03-70f28dad6f95	78197a4b-8a7c-40f5-9c4e-27b2b06be3bd	0	10	f	\N	\N
d1371ed5-6ec8-400c-8a80-393422c76500	\N	docker-http-basic-authenticator	c09c14f3-a5f7-4baa-be03-70f28dad6f95	7d40a63e-ff04-4e8a-b16b-8d9feafd0694	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
f0ff90ae-90c0-4fed-b02e-91c4c5385ce1	browser	Browser based authentication	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
eb3498d9-9dfa-4281-adc4-740849c1c7b2	forms	Username, password, otp and other auth forms.	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
efeeb53e-23dc-45e9-a380-bc3eebb0ba12	Browser - Conditional 2FA	Flow to determine if any 2FA is required for the authentication	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
b1a1a1e0-2acd-475d-9623-6044b79d1659	direct grant	OpenID Connect Resource Owner Grant	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
108085fb-a6e4-47cf-8524-8fd8b0ca8635	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
573bef42-1473-4cd5-8e68-f45c9b9d64b1	registration	Registration flow	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
79dc5e21-504d-4e5a-af5a-cf1d9b24a9d1	registration form	Registration form	74fb1520-0e85-4576-bac4-f72784d7f550	form-flow	f	t
f596a2e9-bcb8-472d-959c-ee2cc3076e11	reset credentials	Reset credentials for a user if they forgot their password or something	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
7972ba4c-0774-41d3-8404-99e6ad1f6425	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
95c24d13-78ca-4699-a279-e28110af37d5	clients	Base authentication for clients	74fb1520-0e85-4576-bac4-f72784d7f550	client-flow	t	t
9bba66b1-bc34-4dcf-ab42-e4eba70a85c7	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
e9efbe13-638e-4d35-b56a-7b2f32c91a04	User creation or linking	Flow for the existing/non-existing user alternatives	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
9822beac-e7f1-4c46-b520-ac7ceb15fc6b	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
0cccd315-e8f8-43c6-9338-e9a8e6510f5b	Account verification options	Method with which to verity the existing account	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
94bc5195-f1b5-4257-b794-b7c0401f5693	Verify Existing Account by Re-authentication	Reauthentication of existing account	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
0b776f91-ba6c-49a9-952e-364d754fdf36	First broker login - Conditional 2FA	Flow to determine if any 2FA is required for the authentication	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	f	t
24ef9b2e-0cd2-4bba-966f-f0b50b9fde21	saml ecp	SAML ECP Profile Authentication Flow	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
d517c279-1a13-45eb-903c-20d31f6e60d9	docker auth	Used by Docker clients to authenticate against the IDP	74fb1520-0e85-4576-bac4-f72784d7f550	basic-flow	t	t
3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	browser	Browser based authentication	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
3721e5c9-b7b3-4e3e-a1a6-d93de9803081	forms	Username, password, otp and other auth forms.	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
4c268c94-8bb4-4671-9961-599feabfb66d	Browser - Conditional 2FA	Flow to determine if any 2FA is required for the authentication	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
1d5ab553-543b-43a1-9488-4582d59968fd	Organization	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
47c55654-4810-4e60-a925-b80d579b0795	Browser - Conditional Organization	Flow to determine if the organization identity-first login is to be used	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
11db3f92-8c30-49e2-8ac1-8adb23bf5027	direct grant	OpenID Connect Resource Owner Grant	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
55d1c76f-b2fe-4f95-b88d-04812faac685	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
96906da9-abfc-445c-8e78-476e6c133599	registration	Registration flow	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
ed9a67da-62e1-42c4-9026-7e1a02190544	registration form	Registration form	c09c14f3-a5f7-4baa-be03-70f28dad6f95	form-flow	f	t
cd630879-8659-4c60-a8be-95c35c372224	reset credentials	Reset credentials for a user if they forgot their password or something	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
13e8aa40-02e8-4612-b946-7c8985bbf3c2	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
f206b76a-9b18-4776-ace9-7a8803097fbe	clients	Base authentication for clients	c09c14f3-a5f7-4baa-be03-70f28dad6f95	client-flow	t	t
6eb93342-0143-4a21-b1c2-c2e4598585c5	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
e3914d2f-41f6-4687-a7eb-3366e48f32aa	User creation or linking	Flow for the existing/non-existing user alternatives	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
8678f91a-7a27-4ef3-b706-5109aa3e8536	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
d19841e2-bce5-42e8-b144-255683ef412a	Account verification options	Method with which to verity the existing account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
a498df84-187e-4669-8f4a-5603afee267b	Verify Existing Account by Re-authentication	Reauthentication of existing account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
86aba2f0-4369-4837-9924-4b5a45ed2a67	First broker login - Conditional 2FA	Flow to determine if any 2FA is required for the authentication	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
eb624147-2b91-45ca-bc32-aee89db84566	First Broker Login - Conditional Organization	Flow to determine if the authenticator that adds organization members is to be used	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	f	t
78197a4b-8a7c-40f5-9c4e-27b2b06be3bd	saml ecp	SAML ECP Profile Authentication Flow	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
7d40a63e-ff04-4e8a-b16b-8d9feafd0694	docker auth	Used by Docker clients to authenticate against the IDP	c09c14f3-a5f7-4baa-be03-70f28dad6f95	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
c580b132-98ed-456b-8915-f93e00b758e0	review profile config	74fb1520-0e85-4576-bac4-f72784d7f550
efd9a0d5-708b-482d-b6e8-e49b0b719e31	create unique user config	74fb1520-0e85-4576-bac4-f72784d7f550
5507efee-53be-4106-a40c-0925395e72cf	review profile config	c09c14f3-a5f7-4baa-be03-70f28dad6f95
b6c3b799-af9c-4798-a126-120a76a7fed2	create unique user config	c09c14f3-a5f7-4baa-be03-70f28dad6f95
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
c580b132-98ed-456b-8915-f93e00b758e0	missing	update.profile.on.first.login
efd9a0d5-708b-482d-b6e8-e49b0b719e31	false	require.password.update.after.registration
5507efee-53be-4106-a40c-0925395e72cf	missing	update.profile.on.first.login
b6c3b799-af9c-4798-a126-120a76a7fed2	false	require.password.update.after.registration
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	f	master-realm	0	f	\N	\N	t	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
45d84e44-7b18-42db-acb0-3deaf693558b	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
2e747951-38af-47c9-b220-67263b5151b8	t	f	broker	0	f	\N	\N	t	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
dd90a450-d5ef-4d13-b211-5a794446a37f	t	t	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
28647af5-d9c3-4b4a-866d-768111a75b00	t	t	admin-cli	0	t	\N	\N	f	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
2477d9ab-3104-4cca-baaf-650e8260bccd	t	f	myrealm-realm	0	f	\N	\N	t	\N	f	74fb1520-0e85-4576-bac4-f72784d7f550	\N	0	f	f	myrealm Realm	f	client-secret	\N	\N	\N	t	f	f	f
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	f	realm-management	0	f	\N	\N	t	\N	f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	0	f	f	${client_realm-management}	f	client-secret	\N	\N	\N	t	f	f	f
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	f	account	0	t	\N	/realms/myrealm/account/	f	\N	f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
c4ac8131-45b7-428a-b2bc-0966287ddf96	t	f	account-console	0	t	\N	/realms/myrealm/account/	f	\N	f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
2e851cb4-3bb2-435a-98d5-2f052922f192	t	f	broker	0	f	\N	\N	t	\N	f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
82806441-9195-44ea-a4e7-cbeb15c17556	t	t	security-admin-console	0	t	\N	/admin/myrealm/console/	f	\N	f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	t	t	admin-cli	0	t	\N	\N	f	\N	f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
f6148499-fe48-40cf-ac62-7d061c084312	t	t	gateway-service	0	f	Grkg5uGKfTJYsWHXtvUbKGfGe4K1V5mK		f		f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	-1	t	f	Gateway Service	t	client-secret			\N	f	f	f	f
e3e3a6ba-b828-4ebd-9415-88988901eef1	t	t	auth-service	0	f	VIMeu5zqAuEQCrPR35KLsN9JGjZZSzw5		f		f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	-1	t	f	Auth Service	t	client-secret			\N	t	f	t	f
3854aed3-37b1-42c9-9bb0-4a40484449be	t	t	backend-service	0	t	\N		f		f	c09c14f3-a5f7-4baa-be03-70f28dad6f95	openid-connect	-1	t	f	Backend Service	f	client-secret			\N	t	f	f	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	post.logout.redirect.uris	+
45d84e44-7b18-42db-acb0-3deaf693558b	post.logout.redirect.uris	+
45d84e44-7b18-42db-acb0-3deaf693558b	pkce.code.challenge.method	S256
dd90a450-d5ef-4d13-b211-5a794446a37f	post.logout.redirect.uris	+
dd90a450-d5ef-4d13-b211-5a794446a37f	pkce.code.challenge.method	S256
dd90a450-d5ef-4d13-b211-5a794446a37f	client.use.lightweight.access.token.enabled	true
28647af5-d9c3-4b4a-866d-768111a75b00	client.use.lightweight.access.token.enabled	true
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	post.logout.redirect.uris	+
c4ac8131-45b7-428a-b2bc-0966287ddf96	post.logout.redirect.uris	+
c4ac8131-45b7-428a-b2bc-0966287ddf96	pkce.code.challenge.method	S256
82806441-9195-44ea-a4e7-cbeb15c17556	post.logout.redirect.uris	+
82806441-9195-44ea-a4e7-cbeb15c17556	pkce.code.challenge.method	S256
82806441-9195-44ea-a4e7-cbeb15c17556	client.use.lightweight.access.token.enabled	true
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	client.use.lightweight.access.token.enabled	true
f6148499-fe48-40cf-ac62-7d061c084312	client.secret.creation.time	1757577690
f6148499-fe48-40cf-ac62-7d061c084312	standard.token.exchange.enabled	false
f6148499-fe48-40cf-ac62-7d061c084312	oauth2.device.authorization.grant.enabled	false
f6148499-fe48-40cf-ac62-7d061c084312	oidc.ciba.grant.enabled	false
f6148499-fe48-40cf-ac62-7d061c084312	backchannel.logout.session.required	true
f6148499-fe48-40cf-ac62-7d061c084312	backchannel.logout.revoke.offline.tokens	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	client.secret.creation.time	1757578109
e3e3a6ba-b828-4ebd-9415-88988901eef1	standard.token.exchange.enabled	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	oauth2.device.authorization.grant.enabled	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	oidc.ciba.grant.enabled	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	backchannel.logout.session.required	true
e3e3a6ba-b828-4ebd-9415-88988901eef1	backchannel.logout.revoke.offline.tokens	false
3854aed3-37b1-42c9-9bb0-4a40484449be	standard.token.exchange.enabled	false
3854aed3-37b1-42c9-9bb0-4a40484449be	oauth2.device.authorization.grant.enabled	false
3854aed3-37b1-42c9-9bb0-4a40484449be	oidc.ciba.grant.enabled	false
3854aed3-37b1-42c9-9bb0-4a40484449be	backchannel.logout.session.required	true
3854aed3-37b1-42c9-9bb0-4a40484449be	backchannel.logout.revoke.offline.tokens	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	realm_client	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	display.on.consent.screen	false
e3e3a6ba-b828-4ebd-9415-88988901eef1	frontchannel.logout.session.required	true
3854aed3-37b1-42c9-9bb0-4a40484449be	client.secret.creation.time	1757849976
3854aed3-37b1-42c9-9bb0-4a40484449be	realm_client	false
3854aed3-37b1-42c9-9bb0-4a40484449be	display.on.consent.screen	false
3854aed3-37b1-42c9-9bb0-4a40484449be	frontchannel.logout.session.required	true
3854aed3-37b1-42c9-9bb0-4a40484449be	access.token.header.type.rfc9068	false
3854aed3-37b1-42c9-9bb0-4a40484449be	request.object.signature.alg	any
3854aed3-37b1-42c9-9bb0-4a40484449be	request.object.encryption.alg	any
3854aed3-37b1-42c9-9bb0-4a40484449be	request.object.encryption.enc	any
3854aed3-37b1-42c9-9bb0-4a40484449be	request.object.required	not required
3854aed3-37b1-42c9-9bb0-4a40484449be	use.refresh.tokens	true
3854aed3-37b1-42c9-9bb0-4a40484449be	client_credentials.use_refresh_token	false
3854aed3-37b1-42c9-9bb0-4a40484449be	token.response.type.bearer.lower-case	false
3854aed3-37b1-42c9-9bb0-4a40484449be	tls.client.certificate.bound.access.tokens	false
3854aed3-37b1-42c9-9bb0-4a40484449be	require.pushed.authorization.requests	false
3854aed3-37b1-42c9-9bb0-4a40484449be	client.use.lightweight.access.token.enabled	false
3854aed3-37b1-42c9-9bb0-4a40484449be	client.introspection.response.allow.jwt.claim.enabled	false
3854aed3-37b1-42c9-9bb0-4a40484449be	acr.loa.map	{}
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	offline_access	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect built-in scope: offline_access	openid-connect
33013151-6388-4ab7-8d48-faab9170c32d	role_list	74fb1520-0e85-4576-bac4-f72784d7f550	SAML role list	saml
b921d787-c0f9-4120-95b1-86e6e44692b1	saml_organization	74fb1520-0e85-4576-bac4-f72784d7f550	Organization Membership	saml
4c8704d3-38e6-4ae0-822c-86a4dade868f	profile	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect built-in scope: profile	openid-connect
6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	email	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect built-in scope: email	openid-connect
042b74c6-0912-4c82-a237-24c8ce2b3bd1	address	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect built-in scope: address	openid-connect
7df7118b-d597-49fa-90bf-1bb838fb8175	phone	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect built-in scope: phone	openid-connect
c67627c2-9400-4e49-9dd5-c2e63ffecf41	roles	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect scope for add user roles to the access token	openid-connect
9f109951-4e26-4cdf-85f5-573304a0beae	web-origins	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect scope for add allowed web origins to the access token	openid-connect
4ab74077-74dc-42a3-870d-a817e982b809	microprofile-jwt	74fb1520-0e85-4576-bac4-f72784d7f550	Microprofile - JWT built-in scope	openid-connect
2970ae3c-c362-43a4-98c2-69ec6751a4fb	acr	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
8103e232-f3e5-4ae8-98ea-090d19460659	basic	74fb1520-0e85-4576-bac4-f72784d7f550	OpenID Connect scope for add all basic claims to the token	openid-connect
c4f3456a-56fe-45d1-9a58-0cb13c1c5aae	service_account	74fb1520-0e85-4576-bac4-f72784d7f550	Specific scope for a client enabled for service accounts	openid-connect
eede082d-086f-46ca-901b-e2ab7fcc32bc	organization	74fb1520-0e85-4576-bac4-f72784d7f550	Additional claims about the organization a subject belongs to	openid-connect
053227ab-d986-433c-af64-8b27c9f0a969	offline_access	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect built-in scope: offline_access	openid-connect
94b1c360-5621-48e2-aad5-ea6c2e3db50e	role_list	c09c14f3-a5f7-4baa-be03-70f28dad6f95	SAML role list	saml
432d2b41-1130-4dbe-8f80-58a226978e9b	saml_organization	c09c14f3-a5f7-4baa-be03-70f28dad6f95	Organization Membership	saml
a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	profile	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect built-in scope: profile	openid-connect
fbcc6996-fb96-41b0-ae93-b975961a4e48	email	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect built-in scope: email	openid-connect
3f38285d-7251-42f8-bc67-0a9ab4db468d	address	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect built-in scope: address	openid-connect
7f670e6a-ed04-40e6-bd85-91dc7f2ded26	phone	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect built-in scope: phone	openid-connect
4a34f71d-f92a-4c2e-88a3-004069c2c6da	roles	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect scope for add user roles to the access token	openid-connect
d0c91b1e-9eeb-486d-97c3-8e5777998483	web-origins	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect scope for add allowed web origins to the access token	openid-connect
32f1d062-4616-484d-a701-0baa63bd331a	microprofile-jwt	c09c14f3-a5f7-4baa-be03-70f28dad6f95	Microprofile - JWT built-in scope	openid-connect
35e67b27-237a-4cfb-ac05-bf478f6012c5	acr	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	basic	c09c14f3-a5f7-4baa-be03-70f28dad6f95	OpenID Connect scope for add all basic claims to the token	openid-connect
c4ee7cf3-4427-4b56-971d-c1c52bcd71f3	service_account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	Specific scope for a client enabled for service accounts	openid-connect
d83fef10-7f01-4b9d-ac7d-c242bfd1b584	organization	c09c14f3-a5f7-4baa-be03-70f28dad6f95	Additional claims about the organization a subject belongs to	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	true	display.on.consent.screen
f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	${offlineAccessScopeConsentText}	consent.screen.text
33013151-6388-4ab7-8d48-faab9170c32d	true	display.on.consent.screen
33013151-6388-4ab7-8d48-faab9170c32d	${samlRoleListScopeConsentText}	consent.screen.text
b921d787-c0f9-4120-95b1-86e6e44692b1	false	display.on.consent.screen
4c8704d3-38e6-4ae0-822c-86a4dade868f	true	display.on.consent.screen
4c8704d3-38e6-4ae0-822c-86a4dade868f	${profileScopeConsentText}	consent.screen.text
4c8704d3-38e6-4ae0-822c-86a4dade868f	true	include.in.token.scope
6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	true	display.on.consent.screen
6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	${emailScopeConsentText}	consent.screen.text
6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	true	include.in.token.scope
042b74c6-0912-4c82-a237-24c8ce2b3bd1	true	display.on.consent.screen
042b74c6-0912-4c82-a237-24c8ce2b3bd1	${addressScopeConsentText}	consent.screen.text
042b74c6-0912-4c82-a237-24c8ce2b3bd1	true	include.in.token.scope
7df7118b-d597-49fa-90bf-1bb838fb8175	true	display.on.consent.screen
7df7118b-d597-49fa-90bf-1bb838fb8175	${phoneScopeConsentText}	consent.screen.text
7df7118b-d597-49fa-90bf-1bb838fb8175	true	include.in.token.scope
c67627c2-9400-4e49-9dd5-c2e63ffecf41	true	display.on.consent.screen
c67627c2-9400-4e49-9dd5-c2e63ffecf41	${rolesScopeConsentText}	consent.screen.text
c67627c2-9400-4e49-9dd5-c2e63ffecf41	false	include.in.token.scope
9f109951-4e26-4cdf-85f5-573304a0beae	false	display.on.consent.screen
9f109951-4e26-4cdf-85f5-573304a0beae		consent.screen.text
9f109951-4e26-4cdf-85f5-573304a0beae	false	include.in.token.scope
4ab74077-74dc-42a3-870d-a817e982b809	false	display.on.consent.screen
4ab74077-74dc-42a3-870d-a817e982b809	true	include.in.token.scope
2970ae3c-c362-43a4-98c2-69ec6751a4fb	false	display.on.consent.screen
2970ae3c-c362-43a4-98c2-69ec6751a4fb	false	include.in.token.scope
8103e232-f3e5-4ae8-98ea-090d19460659	false	display.on.consent.screen
8103e232-f3e5-4ae8-98ea-090d19460659	false	include.in.token.scope
c4f3456a-56fe-45d1-9a58-0cb13c1c5aae	false	display.on.consent.screen
c4f3456a-56fe-45d1-9a58-0cb13c1c5aae	false	include.in.token.scope
eede082d-086f-46ca-901b-e2ab7fcc32bc	true	display.on.consent.screen
eede082d-086f-46ca-901b-e2ab7fcc32bc	${organizationScopeConsentText}	consent.screen.text
eede082d-086f-46ca-901b-e2ab7fcc32bc	true	include.in.token.scope
053227ab-d986-433c-af64-8b27c9f0a969	true	display.on.consent.screen
053227ab-d986-433c-af64-8b27c9f0a969	${offlineAccessScopeConsentText}	consent.screen.text
94b1c360-5621-48e2-aad5-ea6c2e3db50e	true	display.on.consent.screen
94b1c360-5621-48e2-aad5-ea6c2e3db50e	${samlRoleListScopeConsentText}	consent.screen.text
432d2b41-1130-4dbe-8f80-58a226978e9b	false	display.on.consent.screen
a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	true	display.on.consent.screen
a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	${profileScopeConsentText}	consent.screen.text
a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	true	include.in.token.scope
fbcc6996-fb96-41b0-ae93-b975961a4e48	true	display.on.consent.screen
fbcc6996-fb96-41b0-ae93-b975961a4e48	${emailScopeConsentText}	consent.screen.text
fbcc6996-fb96-41b0-ae93-b975961a4e48	true	include.in.token.scope
3f38285d-7251-42f8-bc67-0a9ab4db468d	true	display.on.consent.screen
3f38285d-7251-42f8-bc67-0a9ab4db468d	${addressScopeConsentText}	consent.screen.text
3f38285d-7251-42f8-bc67-0a9ab4db468d	true	include.in.token.scope
7f670e6a-ed04-40e6-bd85-91dc7f2ded26	true	display.on.consent.screen
7f670e6a-ed04-40e6-bd85-91dc7f2ded26	${phoneScopeConsentText}	consent.screen.text
7f670e6a-ed04-40e6-bd85-91dc7f2ded26	true	include.in.token.scope
4a34f71d-f92a-4c2e-88a3-004069c2c6da	true	display.on.consent.screen
4a34f71d-f92a-4c2e-88a3-004069c2c6da	${rolesScopeConsentText}	consent.screen.text
4a34f71d-f92a-4c2e-88a3-004069c2c6da	false	include.in.token.scope
d0c91b1e-9eeb-486d-97c3-8e5777998483	false	display.on.consent.screen
d0c91b1e-9eeb-486d-97c3-8e5777998483		consent.screen.text
d0c91b1e-9eeb-486d-97c3-8e5777998483	false	include.in.token.scope
32f1d062-4616-484d-a701-0baa63bd331a	false	display.on.consent.screen
32f1d062-4616-484d-a701-0baa63bd331a	true	include.in.token.scope
35e67b27-237a-4cfb-ac05-bf478f6012c5	false	display.on.consent.screen
35e67b27-237a-4cfb-ac05-bf478f6012c5	false	include.in.token.scope
1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	false	display.on.consent.screen
1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	false	include.in.token.scope
c4ee7cf3-4427-4b56-971d-c1c52bcd71f3	false	display.on.consent.screen
c4ee7cf3-4427-4b56-971d-c1c52bcd71f3	false	include.in.token.scope
d83fef10-7f01-4b9d-ac7d-c242bfd1b584	true	display.on.consent.screen
d83fef10-7f01-4b9d-ac7d-c242bfd1b584	${organizationScopeConsentText}	consent.screen.text
d83fef10-7f01-4b9d-ac7d-c242bfd1b584	true	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	9f109951-4e26-4cdf-85f5-573304a0beae	t
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	8103e232-f3e5-4ae8-98ea-090d19460659	t
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	7df7118b-d597-49fa-90bf-1bb838fb8175	f
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	4ab74077-74dc-42a3-870d-a817e982b809	f
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
45d84e44-7b18-42db-acb0-3deaf693558b	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
45d84e44-7b18-42db-acb0-3deaf693558b	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
45d84e44-7b18-42db-acb0-3deaf693558b	9f109951-4e26-4cdf-85f5-573304a0beae	t
45d84e44-7b18-42db-acb0-3deaf693558b	8103e232-f3e5-4ae8-98ea-090d19460659	t
45d84e44-7b18-42db-acb0-3deaf693558b	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
45d84e44-7b18-42db-acb0-3deaf693558b	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
45d84e44-7b18-42db-acb0-3deaf693558b	7df7118b-d597-49fa-90bf-1bb838fb8175	f
45d84e44-7b18-42db-acb0-3deaf693558b	4ab74077-74dc-42a3-870d-a817e982b809	f
45d84e44-7b18-42db-acb0-3deaf693558b	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
45d84e44-7b18-42db-acb0-3deaf693558b	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
45d84e44-7b18-42db-acb0-3deaf693558b	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
28647af5-d9c3-4b4a-866d-768111a75b00	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
28647af5-d9c3-4b4a-866d-768111a75b00	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
28647af5-d9c3-4b4a-866d-768111a75b00	9f109951-4e26-4cdf-85f5-573304a0beae	t
28647af5-d9c3-4b4a-866d-768111a75b00	8103e232-f3e5-4ae8-98ea-090d19460659	t
28647af5-d9c3-4b4a-866d-768111a75b00	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
28647af5-d9c3-4b4a-866d-768111a75b00	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
28647af5-d9c3-4b4a-866d-768111a75b00	7df7118b-d597-49fa-90bf-1bb838fb8175	f
28647af5-d9c3-4b4a-866d-768111a75b00	4ab74077-74dc-42a3-870d-a817e982b809	f
28647af5-d9c3-4b4a-866d-768111a75b00	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
28647af5-d9c3-4b4a-866d-768111a75b00	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
28647af5-d9c3-4b4a-866d-768111a75b00	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
2e747951-38af-47c9-b220-67263b5151b8	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
2e747951-38af-47c9-b220-67263b5151b8	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
2e747951-38af-47c9-b220-67263b5151b8	9f109951-4e26-4cdf-85f5-573304a0beae	t
2e747951-38af-47c9-b220-67263b5151b8	8103e232-f3e5-4ae8-98ea-090d19460659	t
2e747951-38af-47c9-b220-67263b5151b8	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
2e747951-38af-47c9-b220-67263b5151b8	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
2e747951-38af-47c9-b220-67263b5151b8	7df7118b-d597-49fa-90bf-1bb838fb8175	f
2e747951-38af-47c9-b220-67263b5151b8	4ab74077-74dc-42a3-870d-a817e982b809	f
2e747951-38af-47c9-b220-67263b5151b8	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
2e747951-38af-47c9-b220-67263b5151b8	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
2e747951-38af-47c9-b220-67263b5151b8	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
41fc49ae-52ee-4330-a1a1-8af3802bc5af	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
41fc49ae-52ee-4330-a1a1-8af3802bc5af	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
41fc49ae-52ee-4330-a1a1-8af3802bc5af	9f109951-4e26-4cdf-85f5-573304a0beae	t
41fc49ae-52ee-4330-a1a1-8af3802bc5af	8103e232-f3e5-4ae8-98ea-090d19460659	t
41fc49ae-52ee-4330-a1a1-8af3802bc5af	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
41fc49ae-52ee-4330-a1a1-8af3802bc5af	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
41fc49ae-52ee-4330-a1a1-8af3802bc5af	7df7118b-d597-49fa-90bf-1bb838fb8175	f
41fc49ae-52ee-4330-a1a1-8af3802bc5af	4ab74077-74dc-42a3-870d-a817e982b809	f
41fc49ae-52ee-4330-a1a1-8af3802bc5af	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
41fc49ae-52ee-4330-a1a1-8af3802bc5af	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
41fc49ae-52ee-4330-a1a1-8af3802bc5af	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
dd90a450-d5ef-4d13-b211-5a794446a37f	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
dd90a450-d5ef-4d13-b211-5a794446a37f	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
dd90a450-d5ef-4d13-b211-5a794446a37f	9f109951-4e26-4cdf-85f5-573304a0beae	t
dd90a450-d5ef-4d13-b211-5a794446a37f	8103e232-f3e5-4ae8-98ea-090d19460659	t
dd90a450-d5ef-4d13-b211-5a794446a37f	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
dd90a450-d5ef-4d13-b211-5a794446a37f	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
dd90a450-d5ef-4d13-b211-5a794446a37f	7df7118b-d597-49fa-90bf-1bb838fb8175	f
dd90a450-d5ef-4d13-b211-5a794446a37f	4ab74077-74dc-42a3-870d-a817e982b809	f
dd90a450-d5ef-4d13-b211-5a794446a37f	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
dd90a450-d5ef-4d13-b211-5a794446a37f	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
dd90a450-d5ef-4d13-b211-5a794446a37f	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	053227ab-d986-433c-af64-8b27c9f0a969	f
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	32f1d062-4616-484d-a701-0baa63bd331a	f
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
c4ac8131-45b7-428a-b2bc-0966287ddf96	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
c4ac8131-45b7-428a-b2bc-0966287ddf96	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
c4ac8131-45b7-428a-b2bc-0966287ddf96	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
c4ac8131-45b7-428a-b2bc-0966287ddf96	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
c4ac8131-45b7-428a-b2bc-0966287ddf96	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
c4ac8131-45b7-428a-b2bc-0966287ddf96	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
c4ac8131-45b7-428a-b2bc-0966287ddf96	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
c4ac8131-45b7-428a-b2bc-0966287ddf96	053227ab-d986-433c-af64-8b27c9f0a969	f
c4ac8131-45b7-428a-b2bc-0966287ddf96	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
c4ac8131-45b7-428a-b2bc-0966287ddf96	32f1d062-4616-484d-a701-0baa63bd331a	f
c4ac8131-45b7-428a-b2bc-0966287ddf96	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	053227ab-d986-433c-af64-8b27c9f0a969	f
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	32f1d062-4616-484d-a701-0baa63bd331a	f
4fe3a7b4-e4a4-478e-9af6-ef5317b30da9	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
2e851cb4-3bb2-435a-98d5-2f052922f192	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
2e851cb4-3bb2-435a-98d5-2f052922f192	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
2e851cb4-3bb2-435a-98d5-2f052922f192	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
2e851cb4-3bb2-435a-98d5-2f052922f192	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
2e851cb4-3bb2-435a-98d5-2f052922f192	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
2e851cb4-3bb2-435a-98d5-2f052922f192	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
2e851cb4-3bb2-435a-98d5-2f052922f192	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
2e851cb4-3bb2-435a-98d5-2f052922f192	053227ab-d986-433c-af64-8b27c9f0a969	f
2e851cb4-3bb2-435a-98d5-2f052922f192	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
2e851cb4-3bb2-435a-98d5-2f052922f192	32f1d062-4616-484d-a701-0baa63bd331a	f
2e851cb4-3bb2-435a-98d5-2f052922f192	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	053227ab-d986-433c-af64-8b27c9f0a969	f
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	32f1d062-4616-484d-a701-0baa63bd331a	f
006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
82806441-9195-44ea-a4e7-cbeb15c17556	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
82806441-9195-44ea-a4e7-cbeb15c17556	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
82806441-9195-44ea-a4e7-cbeb15c17556	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
82806441-9195-44ea-a4e7-cbeb15c17556	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
82806441-9195-44ea-a4e7-cbeb15c17556	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
82806441-9195-44ea-a4e7-cbeb15c17556	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
82806441-9195-44ea-a4e7-cbeb15c17556	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
82806441-9195-44ea-a4e7-cbeb15c17556	053227ab-d986-433c-af64-8b27c9f0a969	f
82806441-9195-44ea-a4e7-cbeb15c17556	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
82806441-9195-44ea-a4e7-cbeb15c17556	32f1d062-4616-484d-a701-0baa63bd331a	f
82806441-9195-44ea-a4e7-cbeb15c17556	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
f6148499-fe48-40cf-ac62-7d061c084312	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
f6148499-fe48-40cf-ac62-7d061c084312	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
f6148499-fe48-40cf-ac62-7d061c084312	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
f6148499-fe48-40cf-ac62-7d061c084312	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
f6148499-fe48-40cf-ac62-7d061c084312	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
f6148499-fe48-40cf-ac62-7d061c084312	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
f6148499-fe48-40cf-ac62-7d061c084312	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
f6148499-fe48-40cf-ac62-7d061c084312	053227ab-d986-433c-af64-8b27c9f0a969	f
f6148499-fe48-40cf-ac62-7d061c084312	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
f6148499-fe48-40cf-ac62-7d061c084312	32f1d062-4616-484d-a701-0baa63bd331a	f
f6148499-fe48-40cf-ac62-7d061c084312	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
f6148499-fe48-40cf-ac62-7d061c084312	c4ee7cf3-4427-4b56-971d-c1c52bcd71f3	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
e3e3a6ba-b828-4ebd-9415-88988901eef1	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
e3e3a6ba-b828-4ebd-9415-88988901eef1	053227ab-d986-433c-af64-8b27c9f0a969	f
e3e3a6ba-b828-4ebd-9415-88988901eef1	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
e3e3a6ba-b828-4ebd-9415-88988901eef1	32f1d062-4616-484d-a701-0baa63bd331a	f
e3e3a6ba-b828-4ebd-9415-88988901eef1	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
e3e3a6ba-b828-4ebd-9415-88988901eef1	c4ee7cf3-4427-4b56-971d-c1c52bcd71f3	t
3854aed3-37b1-42c9-9bb0-4a40484449be	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
3854aed3-37b1-42c9-9bb0-4a40484449be	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
3854aed3-37b1-42c9-9bb0-4a40484449be	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
3854aed3-37b1-42c9-9bb0-4a40484449be	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
3854aed3-37b1-42c9-9bb0-4a40484449be	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
3854aed3-37b1-42c9-9bb0-4a40484449be	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
3854aed3-37b1-42c9-9bb0-4a40484449be	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
3854aed3-37b1-42c9-9bb0-4a40484449be	053227ab-d986-433c-af64-8b27c9f0a969	f
3854aed3-37b1-42c9-9bb0-4a40484449be	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
3854aed3-37b1-42c9-9bb0-4a40484449be	32f1d062-4616-484d-a701-0baa63bd331a	f
3854aed3-37b1-42c9-9bb0-4a40484449be	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	2149de2f-02f5-44f6-9ef2-c0e10e0a1d7e
053227ab-d986-433c-af64-8b27c9f0a969	3e13666b-02f3-4571-b75b-1721edaf8537
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
0288f3d7-161e-410e-a093-9f6b829e180d	Trusted Hosts	74fb1520-0e85-4576-bac4-f72784d7f550	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	anonymous
34e42c3b-67b9-4d62-a145-69d5ced14ffd	Consent Required	74fb1520-0e85-4576-bac4-f72784d7f550	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	anonymous
83812907-0abe-4d34-8049-8d6bbba2e48d	Full Scope Disabled	74fb1520-0e85-4576-bac4-f72784d7f550	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	anonymous
73bf541b-ee5e-4dd0-a26a-b47d78046e96	Max Clients Limit	74fb1520-0e85-4576-bac4-f72784d7f550	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	anonymous
54943f10-99aa-4464-b989-858a33d87391	Allowed Protocol Mapper Types	74fb1520-0e85-4576-bac4-f72784d7f550	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	anonymous
bcc897b9-f6d0-46af-8830-13e0b6e82ae8	Allowed Client Scopes	74fb1520-0e85-4576-bac4-f72784d7f550	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	anonymous
ec3c4792-8cc8-4fc9-8c14-95e16115b377	Allowed Protocol Mapper Types	74fb1520-0e85-4576-bac4-f72784d7f550	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	authenticated
d7449b93-9e42-4ec1-839d-c92d4ce6a1e3	Allowed Client Scopes	74fb1520-0e85-4576-bac4-f72784d7f550	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	authenticated
9aef4ea1-014e-4049-ab83-e3d79c1786a0	rsa-generated	74fb1520-0e85-4576-bac4-f72784d7f550	rsa-generated	org.keycloak.keys.KeyProvider	74fb1520-0e85-4576-bac4-f72784d7f550	\N
d6505d4e-0eb9-4236-83e6-44500384e1ca	rsa-enc-generated	74fb1520-0e85-4576-bac4-f72784d7f550	rsa-enc-generated	org.keycloak.keys.KeyProvider	74fb1520-0e85-4576-bac4-f72784d7f550	\N
d924abab-72bb-4205-8ed1-d99b5c327a27	hmac-generated-hs512	74fb1520-0e85-4576-bac4-f72784d7f550	hmac-generated	org.keycloak.keys.KeyProvider	74fb1520-0e85-4576-bac4-f72784d7f550	\N
46abf047-4d85-4772-bcf2-236289df1d1f	aes-generated	74fb1520-0e85-4576-bac4-f72784d7f550	aes-generated	org.keycloak.keys.KeyProvider	74fb1520-0e85-4576-bac4-f72784d7f550	\N
94260ab0-b404-4af7-bc89-c7d56c7d936c	\N	74fb1520-0e85-4576-bac4-f72784d7f550	declarative-user-profile	org.keycloak.userprofile.UserProfileProvider	74fb1520-0e85-4576-bac4-f72784d7f550	\N
9e21e75c-e1cc-4868-8f7b-243ca8911bcc	rsa-generated	c09c14f3-a5f7-4baa-be03-70f28dad6f95	rsa-generated	org.keycloak.keys.KeyProvider	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N
2bc73ac6-1f42-43c1-9c37-7cd24fa76230	rsa-enc-generated	c09c14f3-a5f7-4baa-be03-70f28dad6f95	rsa-enc-generated	org.keycloak.keys.KeyProvider	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N
bc98a599-6985-4b64-839d-de53979c73be	hmac-generated-hs512	c09c14f3-a5f7-4baa-be03-70f28dad6f95	hmac-generated	org.keycloak.keys.KeyProvider	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N
3fbe49c1-49cb-43a3-89da-7ccc7bf244ba	aes-generated	c09c14f3-a5f7-4baa-be03-70f28dad6f95	aes-generated	org.keycloak.keys.KeyProvider	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N
a5d5b21d-0c83-4217-82e6-89c7fec3056d	Trusted Hosts	c09c14f3-a5f7-4baa-be03-70f28dad6f95	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	anonymous
5acf5d02-7ed8-42f6-a01f-38da76b1b2bc	Consent Required	c09c14f3-a5f7-4baa-be03-70f28dad6f95	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	anonymous
11035701-63f5-48c3-80ae-58da79f1ea5a	Full Scope Disabled	c09c14f3-a5f7-4baa-be03-70f28dad6f95	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	anonymous
8fd4d363-b69b-49c2-9db6-6e2448eb572f	Max Clients Limit	c09c14f3-a5f7-4baa-be03-70f28dad6f95	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	anonymous
da56dfd3-4d16-4e1d-b047-ada66713fc30	Allowed Protocol Mapper Types	c09c14f3-a5f7-4baa-be03-70f28dad6f95	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	anonymous
715bdb05-35d8-4731-be23-a69fc11893f5	Allowed Client Scopes	c09c14f3-a5f7-4baa-be03-70f28dad6f95	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	anonymous
ecadf162-b455-42fd-b6cc-791134a91985	Allowed Protocol Mapper Types	c09c14f3-a5f7-4baa-be03-70f28dad6f95	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	authenticated
cbd6316d-d76a-4e80-aa89-fb61bc55ea58	Allowed Client Scopes	c09c14f3-a5f7-4baa-be03-70f28dad6f95	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	authenticated
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
c0d0e039-bdfb-4b89-a582-7310a2b09a20	bcc897b9-f6d0-46af-8830-13e0b6e82ae8	allow-default-scopes	true
aa4dddf4-669c-4356-a79d-7af56824322e	0288f3d7-161e-410e-a093-9f6b829e180d	host-sending-registration-request-must-match	true
d3464d13-30a8-4a3c-8d95-e37a9809054a	0288f3d7-161e-410e-a093-9f6b829e180d	client-uris-must-match	true
8c7b8568-5f97-47d7-af70-c8a7a276f332	d7449b93-9e42-4ec1-839d-c92d4ce6a1e3	allow-default-scopes	true
b2a3a325-012e-4e30-9963-bfb8507de313	73bf541b-ee5e-4dd0-a26a-b47d78046e96	max-clients	200
ac83d8ad-0e4b-408f-8fd1-f8b062b326f7	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	saml-user-property-mapper
2df25bb8-5677-4af0-9cf2-7aafd896558e	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	oidc-full-name-mapper
0311337f-df93-458c-9164-133c135c6121	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
50d78084-1d46-4fe9-b994-7a8ebcd10b13	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
4d63fdbd-5a04-4c85-879d-162958b9b846	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	saml-user-attribute-mapper
290ce972-76d4-4e3d-8060-cf83b9c42810	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	oidc-address-mapper
0aef80e1-6310-4d4c-a0e7-93ad7616eb9a	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
79878607-43d2-42d0-b50d-9fe251d7db01	54943f10-99aa-4464-b989-858a33d87391	allowed-protocol-mapper-types	saml-role-list-mapper
a417d9b5-c83b-4ad7-bcba-23fbb5e83baa	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	saml-user-property-mapper
e2d3b28e-9944-4a6b-a669-69f870c80998	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
96e1217f-b1ac-4424-8c77-e3ba6ce24391	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	oidc-address-mapper
cc2bb8e4-8119-4cec-843b-a741f4994b6d	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
d2d9fa5f-cd02-40f1-9b59-a4fd7bbd1352	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	oidc-full-name-mapper
c254c838-deed-4c44-acec-085bf2d9d038	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	saml-role-list-mapper
1e079ce6-121a-4515-85cf-5301da861fd1	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
dc7b7ac4-0ebf-43bc-a872-4935e2d7c8b0	ec3c4792-8cc8-4fc9-8c14-95e16115b377	allowed-protocol-mapper-types	saml-user-attribute-mapper
c2f01cf3-edd3-4a84-ada7-9b8557bf8326	94260ab0-b404-4af7-bc89-c7d56c7d936c	kc.user.profile.config	{"attributes":[{"name":"username","displayName":"${username}","validations":{"length":{"min":3,"max":255},"username-prohibited-characters":{},"up-username-not-idn-homograph":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"email","displayName":"${email}","validations":{"email":{},"length":{"max":255}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"firstName","displayName":"${firstName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"lastName","displayName":"${lastName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false}],"groups":[{"name":"user-metadata","displayHeader":"User metadata","displayDescription":"Attributes, which refer to user metadata"}]}
7569e869-20da-4f88-b798-a0a3749708d9	d924abab-72bb-4205-8ed1-d99b5c327a27	kid	d003a9da-a074-4d57-8cca-ce057c82af0c
c8d00014-75f4-4009-8421-a47eac9546b9	d924abab-72bb-4205-8ed1-d99b5c327a27	priority	100
48b149e3-2d24-4a66-919e-b019223977bb	d924abab-72bb-4205-8ed1-d99b5c327a27	secret	TBnk2qh5DrFI6EyfQjqmr7bcC9U3dxBFXgQZDmj7_itbRF0hEzf6DcfTQjFPnWpLC6_oKlo7yCu-IFQY9IbmMvD1d-Kk8qksDsAoD7wdGxiPa6gXrF48SnCxfhcBKf2bE1sMk8HB5rmzB-wEpDDbmYaUsnx8nDXC-OmPnqvstjk
b3ed338e-8266-4bd6-897e-77b44ba63aed	d924abab-72bb-4205-8ed1-d99b5c327a27	algorithm	HS512
5ad456e2-78b1-483c-964f-88cea6a9b1bf	d6505d4e-0eb9-4236-83e6-44500384e1ca	algorithm	RSA-OAEP
c73ce16f-d54c-4f9d-bdf3-db63018e6f74	d6505d4e-0eb9-4236-83e6-44500384e1ca	certificate	MIICmzCCAYMCBgGZNzVrTjANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUwOTExMDUxNjExWhcNMzUwOTExMDUxNzUxWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC48DSFUI0s7qKFvA7xxMrMipSV6JvFFTIjClSkPqumKocXL0Kaj47AnTu9tFiigfONHhlXQfJzXV4w6gUKwCiTDvaOt1JmG3+5AMcpVx1D6DhDS3TJzFbAmentoFiKyqzD51TE9gSP3lEshxAMK3EbXUNHj4iKuJFxXP7oQvRcyUmAO1qpdF6cBfnLcmpBc6aoRyynQPs4RUYmjm6+DMUe3QfQSIMXZVldc1In10TweyqZOiMzuulfKZZYmILjFUMyqlpyJAh38ONnBXc+VGyvxQAa2jY2bXM7gdzrfqNCN6snkrDM8FOw4N1knslKTlTO6uHnnGfWhUQVLrTLxJNXAgMBAAEwDQYJKoZIhvcNAQELBQADggEBACsMhArVc/zzT9Fe+164AMToxFAvklztAkdHpLDukwKvuvdvZ+JfJg6UroaXOxLoP5XhUmOyMQycQIMJRZHFQM9guYQfm0SRpohreT9psHEcC8DnfM87F29OWsLvLpc80uNBMP7UCvxGeSkmATXpbz9Er4s/F7jEBhNQakh1URaJeJqD0UR60wfArok4rxyiRcze5itFsS/P77N6q/30rAhRRj8Xn9laexp3Tt4adLiBwFccLH8Aki+4FUI56RZT6K+1FvsowCMUahG/b+TvPPpQrDpEkivi+HULptymN7wiEgGluWYAd5jn9PG3krdCIyEbg/Y5fnsfUqiKaD9eeXc=
609cc424-6126-48b2-8638-527684cb9215	d6505d4e-0eb9-4236-83e6-44500384e1ca	privateKey	MIIEowIBAAKCAQEAuPA0hVCNLO6ihbwO8cTKzIqUleibxRUyIwpUpD6rpiqHFy9Cmo+OwJ07vbRYooHzjR4ZV0Hyc11eMOoFCsAokw72jrdSZht/uQDHKVcdQ+g4Q0t0ycxWwJnp7aBYisqsw+dUxPYEj95RLIcQDCtxG11DR4+IiriRcVz+6EL0XMlJgDtaqXRenAX5y3JqQXOmqEcsp0D7OEVGJo5uvgzFHt0H0EiDF2VZXXNSJ9dE8HsqmTojM7rpXymWWJiC4xVDMqpaciQId/DjZwV3PlRsr8UAGto2Nm1zO4Hc636jQjerJ5KwzPBTsODdZJ7JSk5Uzurh55xn1oVEFS60y8STVwIDAQABAoIBABgDTHaA8ovCgoTF2dTnngN3/PoIBW3OJj/pf/sBji0ZuYe3objK6hi1ccPcjmOvZu3MWjL7w5zaIzhLspLmR35sw3Pddsv+RxPA95s1Df46wU9vlT78kskYUwkBOG3xCgS33Uhjv0zk63pSITHLVDje37UDfJGyjGQBVLHYu7zuRz1t+E/lulZxawlwRbLu9Tc+NOU63+u0tRUvX4v6yLYje3JZOhnSarArUAfAnpaJmxH15q+GwukAh2YJo98t8FK3Npa3oUeHmzHDuvTX9aWIJ2yz+0NY6dRtFAC1P0P+Oj85xaveNOYRoKjiVsY+8vb0r2/A04NKIRAx4T2hysECgYEA6k+r8S+SifoGanvEonDnKB3kwPudZGhKzNoPVeX9vGIBlfzS2inMqDY2+e7PM3inbuT5happMQ30FTYjuMkUkt/3xHdceo3nPwRKJcUGcs/X/UjixYaaKtypWWiqEun5oPcqTBQte/MxVeDkd0pYVKdaG9D7qTiP6Kr39Z6i9MECgYEAyg6S8IrVUtheEcKHw+XEs6m0SkOZr9rdbx5dK5sVYEjY7hfZtLCCo95bbjJ71huJ29EalbOiTUzoqGRqDVweN1Xiqn++i1ZUG793zZVQuqNq9YcAAAb9DI2hEi0JyIT2YzGvj2/TeJ89ZhNoviJiqGMkdaFpo0CLExz9aJgYFhcCgYEA0HVlYcSTONIKVz4Z6PNNlHFUDSVOGAB5W9s8/Y0Ku0juZER0SsgZXiYtrxp5mS9kttJb9EFyKMRjPWPXUBUFis5bnp0a16Xb8EKevQ+W9xpW7O6lYlqoWi3LNNJbTUyAXGbJaLoi5T2tzNuyJzcrctUY49p/q0O5NKS2yf0CuYECgYAtJVJ5glullgh2qWNSRt1LU0igKziEjT9am4svugftqnklwZh4AdQ+ruj41zjKHv8PaUoqVqeV4/A756u2bnWl4X+ynrZBRbmnNYGcIpeWC/tS/qXmW6lgi6iIeh2seuDeSvSx9MdoNRQv4XhOuzoV64jy62zB+CLa02hfXVRMYwKBgFtVhFQ6lUnz6UC4dicL0thRmf8p851HPnUUfdR/VjmOVYfmT6emDX2DvEJcj7IjQmSptd0to3q/+UY/VLt5wO6hb5TJ4FqYh6u5P/RVrMGn6gh+Oi6BlM5KHW/5Qdk9qMFiqw+H2tuPdzCSJeRlQI8kDzabhN1k2GXeqvoN3zbO
28b48acf-5b9e-430c-b990-a77ce46ff145	d6505d4e-0eb9-4236-83e6-44500384e1ca	priority	100
a67c4b07-507e-4e35-9261-e00206975d6b	d6505d4e-0eb9-4236-83e6-44500384e1ca	keyUse	ENC
ce2664d0-e06f-4bfe-8bf3-a80c069f1c38	46abf047-4d85-4772-bcf2-236289df1d1f	priority	100
45a6477e-2bcb-49ac-9525-4d009aa8d7dd	46abf047-4d85-4772-bcf2-236289df1d1f	kid	88a3467c-ea64-493e-b866-c2c5a6912576
f7444f8d-7492-439b-a971-d2d9e31d33c7	46abf047-4d85-4772-bcf2-236289df1d1f	secret	URp34R38z14ayVJS6JHfPg
073bfdca-2baa-4d2d-be6b-2f97f14337a5	9aef4ea1-014e-4049-ab83-e3d79c1786a0	priority	100
ffad6f53-340e-4f71-9d15-3a760ff63cd5	9aef4ea1-014e-4049-ab83-e3d79c1786a0	keyUse	SIG
93a921ae-c684-4e8f-bde6-7af377dc15db	9aef4ea1-014e-4049-ab83-e3d79c1786a0	privateKey	MIIEogIBAAKCAQEAoz0AIkk39lFo8WQI4/6+gWDMPFifYADbYph8bdBDc3GtCD23RZ/JSLptgbsIy2hJhUe5u4pYCYG+yrIGPjkQb43GEbLUojSK6CPzMF188qtewhrwRX6/7vYHA3AMjUFAelqntZ6aOcpudYH3+KzGB4grezOnRS0FkG8jdaKRqhV4nG73t3vIh3gcdJX9u3TYviUab4BYph+rJSqvGX1UWmGIPH29ktE91fujJqGSjSl7e2p/+xc5buPR/hLuQa8NsgpIZd2WoflmSDUIzFk0Vh5lZpTlkjhfH1XpjGftGvI/9O0fN3cmqueXEFKw1OOSPOmCal2K2jZbj0XMzQOeowIDAQABAoIBABZcuvH3edUine/DtnwVX9+1dQUnVt3+AA/nt9QLXdIo1n10hvDgzOBLuci16KSE6t15T/ZjI2BaB9sYvEY15wyBLYsrJnn6HLVkh2IuzHkqBNcufY/npz+DML7F0NJhCAY6+RKmYZZdJviuD1xfvtk1TE+aaeXOHx8EOqVY2f+krViS6wm2t0CcZEZTSz46bjfglndXs+4W/BkjDADgcUGyrvaujGbt8xE0maEjoS9gaPe5LRAEJ3RGhM9GgxRepo7E0/aB4Pju8M+4F7Y7zypE4/WmgaM5LG9xB1f+JCjJV2toViHauBWSbbQuhthI/pF/Z/NFDO7v4d28B1igOHECgYEAzY6HGUHznwp2hlrH1tukqpWbvnnUvdwg1x1Y95OEKFgmxrUYwnht6+XQhLSxMzZKQPtKAl2oSXVmLs366WbdxLrewALHMHVjM/aT1qPBtQCVgZRlvMyqRCb75NQC4p+Jgu4njJw2qOfh+xvIexw0SlDiroPJmxP3sJ6kI8ug73UCgYEAy0vx6sJbI6Iy4/si6H+61sg/9DooD6Vbb9QP2F+x02H7UleBLdDs7e9D9Z7IyRkfNFZvZTrakJxr3ucAvcW5iOkomnvmOZymV8SEEhlvmEDU9YqZtVqpNBogrr/hTR8mzsxEh+EzjC6BcDJ3TeDWUixMXGg7TxGrgwf1hJpKarcCgYA7Xh7GvLWQCYWR6EYvV+CHJA6HRMiczSbu+hrn5kMVI+CPI16zS/TzXLyMl2NWVye6/NizNjmUfcO1YOhG4N7MIuySqDq/VCgCqgSoyUL48+I6OpHnpXTHmjrGLDVpHoqotf/WLIfc4Jvz2mJcrBiuHg5aONXRZEL1T9w4OmeQwQKBgCp3cM9zTgqBhVPuT/p1RBg9uG5R/7BVAGa5y52NT94yJ4ep08g4TmXdhzq1soefgc8zBw4EX0e6BSaI5rS9quYYnmy22P7L5EuNN3F4csFBObsY+sbRa2sA0jbuyieMDFVCgkZIGrZ7uHoxcI6KGvsLWeArvcfkjwlODlFm0Yi9AoGAQk62/S9TvOomj7OAXSglR/DxNIZmMCjPVR66hhE9O2+tVXuBC/GeqvgSpoMdlGNOe+b5W80gjqvDT4DcQq/C6AZwbx84jKpObqPm2UCnAn/cA1AUQS9gi0YCBwIinm3vUmAeEIKs4pzfUZkuaZlYWGQMFa60JCGG4TvA0UUqxJg=
fd6671de-ab8c-475d-880e-01ba9a5f1334	9aef4ea1-014e-4049-ab83-e3d79c1786a0	certificate	MIICmzCCAYMCBgGZNzVpNzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUwOTExMDUxNjExWhcNMzUwOTExMDUxNzUxWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCjPQAiSTf2UWjxZAjj/r6BYMw8WJ9gANtimHxt0ENzca0IPbdFn8lIum2BuwjLaEmFR7m7ilgJgb7KsgY+ORBvjcYRstSiNIroI/MwXXzyq17CGvBFfr/u9gcDcAyNQUB6Wqe1npo5ym51gff4rMYHiCt7M6dFLQWQbyN1opGqFXicbve3e8iHeBx0lf27dNi+JRpvgFimH6slKq8ZfVRaYYg8fb2S0T3V+6MmoZKNKXt7an/7Fzlu49H+Eu5Brw2yCkhl3Zah+WZINQjMWTRWHmVmlOWSOF8fVemMZ+0a8j/07R83dyaq55cQUrDU45I86YJqXYraNluPRczNA56jAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAGA+EmNvquOG/ukkCmcDN6IdoL7VmuFjLqp9xs4QICvP5VLvxvF/L95kx19PLL14/SMChT6HBY2zSR4TlrXIeDw2N7DPUNv1HyWQcNFTFWX2vuq4UazS3a1vDR+/WXy0MP/Fcx8eNHuzfKoESrPf2yy3ZQ0QLo/gaf7ZD19E5s+Jmw/bGYu3ZNYIoge9NNR746IwsqSONNx6vihH8JiiN/vRF2z7p/HtsovtAmRmaSYyO362nEY37s4/47cpa9iYNOkAvWRnYTUV1N2w4cpUrbQVqWbKnenGRmNcPtJ6aaz3S9pJBVKRyBnJkXK3vK9LKbv7dselWmHA4lMUXDYH2+w=
a04a1381-8505-429c-996a-5dfb1dff6aed	9e21e75c-e1cc-4868-8f7b-243ca8911bcc	certificate	MIICnTCCAYUCBgGZN8cVqTANBgkqhkiG9w0BAQsFADASMRAwDgYDVQQDDAdteXJlYWxtMB4XDTI1MDkxMTA3NTUxOFoXDTM1MDkxMTA3NTY1OFowEjEQMA4GA1UEAwwHbXlyZWFsbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAOFuQvxrX+iXRaDYoZ7DstHsv9xPiBZ5dV/nikJRujiViTkg0g08+tI0kIz/+i2F3vzP1UWejFLdxG03v9FDBRSiP/jmLwm86yI4Bc5yI3jwV2JhL32qqldyfZQKbLtybjTeoy9NfcLm8ePimrF3n4rQc64fn9bGr98tnrU23OXIkPq8WoPTPHwrZorITs81O8l4WqwchTR7KYb5UYHxSgZLuZyxYUwZmIz4ArrUjnRJsJ2S8a22qE6KyIPn5YZeYPrzMdUu4jKL2VtkFAcL/ngfGBXS6ooqfS5bj8Q8mzcSoYgBylA3py8ldNvsz9d8cyqvadXifQt/uyAcoDEXs/8CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAxWxstsgiX7+1DBCLb+vE6S4ezMU3lHO3esog3MHoB2Revz+xF67MJYbEHW++TXR0jl3bAi7hCae/ItPnWDtpFxL9a6f4cAC4j5YqVG8l/+dRuygBV5vNgpNvrqkFzIefXSq5KnDsyRXFDqMJZxv5iTOcixop5W8Co1B1dt6Qg2WHaL1jgXvzsncSrcBbVBxJRTdtm1z+pD4yI2MS8G1zXPO43FqLCVWnSZz99HZYedBt4uIHTKFDbpROBsQA7rtxKPIkFv83fGAWvabGpDkaqqHiSy+IGxThurXjeKQ/+5t4cFgmYeajylBuA74SCOh3zuzzEfZxT92pG4Ym40tb+g==
5c3c9807-264f-449e-b3e6-50773388f068	9e21e75c-e1cc-4868-8f7b-243ca8911bcc	priority	100
72af9d58-a288-4a17-88b4-39b5ce385ae9	9e21e75c-e1cc-4868-8f7b-243ca8911bcc	privateKey	MIIEpAIBAAKCAQEA4W5C/Gtf6JdFoNihnsOy0ey/3E+IFnl1X+eKQlG6OJWJOSDSDTz60jSQjP/6LYXe/M/VRZ6MUt3EbTe/0UMFFKI/+OYvCbzrIjgFznIjePBXYmEvfaqqV3J9lApsu3JuNN6jL019wubx4+KasXefitBzrh+f1sav3y2etTbc5ciQ+rxag9M8fCtmishOzzU7yXharByFNHsphvlRgfFKBku5nLFhTBmYjPgCutSOdEmwnZLxrbaoTorIg+flhl5g+vMx1S7iMovZW2QUBwv+eB8YFdLqiip9LluPxDybNxKhiAHKUDenLyV02+zP13xzKq9p1eJ9C3+7IBygMRez/wIDAQABAoIBAG4WXw8OzqjyyrV4ZUD3l8mNgMpxamdzS/8YPvwRFkxQtlEYNovxdhmWQusgjIeToyg5xJB5k1RaaXTer0VdocsISw5dNqZeREOTMT62nZ0PXdU02bDYWdRXnHzhtGS7xpb6DJ3qjVpTVtgvTtUQsnBjSMLFPdTwdW2bRTCAyNJLO99KNwZgalsL1h75BE5lOlI4BY4whKgst59oABsNVyT0NcQqXlFnZ3lDZ1B9LNfQE/1oybj8TqBAPtC7mCakDMhIPWiD/XueqJbk3gVji8FA00CSyOd+P2LzMPsfyJUZY8PkQpnLbZC+OlIK/5WUBepMKgt5qZM/8UcYNlKj2qECgYEA8zlL+wm79CDQ90vNlPF2kUpjHd8a8d/ANk+jcvSU2QI7XkSt3mbfIis6+fbOjg8XVcV2KK3PSsuOoGmh8BmYcsbt3v6cn/fOBa+yXHe7N2GINmMQmPCuCDspnhltq5gnDKxRqv7bghKOZl6ES2s0dyo1I5sVnhCwKQbe8K2h9F8CgYEA7UWyFtWbRzmuMotIl8WNSozi4upu9RVslzhYpyGuQJzBbh4/rtfTEOH6NHwDfsgYbZbzjcw6D43dTvcgfzQhq5HmQBxatN9ZZz3TdPuvCxSNkyyT35r5BTEuh2+FxtLOH2mUby2a0DLAqo7JiujRnfj3T10Adp593bALOKrbZGECgYAsxrKPL5aTdesia6kYL2+1ha1DlYwfVSLMnNKpWg+A9+E4FIqxs9yGLNE5rU8PZgXUu3pqOL09tsW2EFxImF2xW5/0+oiZBoS+FT9PuJcVtfXelVQ746sWRtX9jg7blTIzKhIK2cpNYt8mR2YxgDXzbijEOku7grT7iJFIW57CHwKBgQDrRDtE5wLzyd7PYUpWq1zg+feS1q+0tkyZWRaWig/1IzmtDbNJk9lGGTv1xK3WjWOjbpULGeDMQXeab/I6Dx//tkhfc34BcKF+fFBq45B6ZSPINeBeTJT0sAKZ6lv0fFxDy3WSE98458oglDqFUTtf71iRyf7Gn3rgUUmfI9MsoQKBgQCMLgX+AxHYdJvXDk0CPqJfHwCJlpFIs21nyo9X3JAv9uVeiFGhJzx4TpI/NbCB5/UIiMxi/VJmvM2F3VAME+UqEPPdS6WOZUUpZHO3pwNlsRsf0BA8hjnvDF+bLVSDIV6XxFILwgc2BF01xoKfPy+dLdnVGIgjHYT7LVv/9o8IQg==
d8d30143-5bb6-485b-8a1d-2a07b7411193	9e21e75c-e1cc-4868-8f7b-243ca8911bcc	keyUse	SIG
53c4f008-b85b-4a95-aa95-0131f8919206	3fbe49c1-49cb-43a3-89da-7ccc7bf244ba	kid	73ab65f7-48ee-453a-ac96-cebf0cc9dbf9
02bebc83-d050-44b2-be5b-5e9969700655	3fbe49c1-49cb-43a3-89da-7ccc7bf244ba	secret	Sevcv-8YWZ51Y-4JpOzY9w
7d91a595-4afb-4f1e-8534-bc149398bd15	3fbe49c1-49cb-43a3-89da-7ccc7bf244ba	priority	100
c6eb0d8e-e65c-4311-9b25-7e3e92befa04	2bc73ac6-1f42-43c1-9c37-7cd24fa76230	algorithm	RSA-OAEP
b95c6545-ab46-48ec-a7d8-770e6a7fa670	2bc73ac6-1f42-43c1-9c37-7cd24fa76230	keyUse	ENC
66f31f2b-e06e-420b-825a-b4ee59de59a8	2bc73ac6-1f42-43c1-9c37-7cd24fa76230	privateKey	MIIEogIBAAKCAQEAojvqj0GSM23r3/d6LEKIvt5oaDap+T11TKglDnNfiCVG4ZIBQYc4XL7MKTXv2yVCD2D822fStUG5w5XzAikvjAYlMDDTWKGCnxWTZypHyar9MwTlOiUSaUnNXrOlCja4m5KLm9HDW7zQ9OmYnvbuz+bqDgWs4WaQOTVYomlX42ciLrZeFMicSFZ/FHeEnXVfEuxCL4IppJBYyoMLOwt7XFAg0Lk/aj8X/WIoMzX0KVDoiHoGFKveM9mDT3GZ+4/xC+xEjJaJi3LpIM7wYe0oPHA45nVaFXd7aNpInBGmo5riNujK5p/6A0n+LxlK3qa1iIChkyoVKQpUlNPyLoa7FQIDAQABAoIBADk8fU0MW7nESqcKJzLNSHRagChgPMh/q84fNm+qOaaNTDwR6FqHi9WAxQO3EUfWFb/aPmtj7j1fklvUH9d/01QJ6P3fA/lthel9UbNggdI+/rSMwhio8p1t2cRTCeOYGbpdUd7n+w33CBuX/G38/ZUgySuoSe19410jmoE2PDkwXlDjFoFFkUZ0UjmJ7m1i89VZeOWapB1dntEx6HQcM02/hwrb+UnIRNlNqOl/JgPG7AJw5SWC+7FPGP1K7ZQMklwxjy2HvMc76XRCFZBSqSREf+gnSbI8WJpIRtIyN69d8LOza9WVWOVwL42egH4P/NPurEZ3ZvSPGPYpGIhxxWMCgYEA0Qr59zo8VuWJG+rbSfN/ZT90i9wZvnT6ZgE7gLAdWQjw9Lhcl+Pg3mFG73NqsihQkMzNR1RaU7yA/qw7rOPP4IOXwkaWWqXQHcSFCQqc7xJnSJnGa82Pt1NNytGD12GBgL/xZnNMKa9ZsrRMIiMi+TUPH5uubBnEJtxFKV6pD48CgYEAxq0x+diud1p4NuSWBhGSzVGq9iYx9uWsH5uHFkPRpZyhlByf3AZ0B5/rp3pd3et9P2jYu6WHTGeake49mEsBlArMGpsW7VW1p0Ivp3C7aUReM6jZfjXbi1g1crg2fInr5XrAagwc2lVxixWrbVx5JYQPb4TyhMK+h8nResxw+RsCgYA6aA21VNEdGl73rOP+Qol6uYMPvLx4qWh47ak5aHuar9PbHFPKoIHowFc4eg/6nSb9fitw3ZGrScTk4yx9Pt2LBll1j5Fc3G1Mk+bzwsJcTdELyONWpYWdHCplG6rxl62+UmLZVCeYkgTG2HdZJmYUKQ4Dr9TJIV3b2bl9OJSV4wKBgFMMZN6en9dOXv/7L2ccA3SGFePM9YfSW8/ekGpTMRh8T+J80KoFnIzvYW9hrEkuve06o5ZUzCi5ChjEMMUvXVqH5OeeOsg80rE27nqcmgqXxJTtmPS0CLDHz5fDvyrx8ES9uh7aL1lo7AyKo10UkaOH/3fLaCDs0v4p88HUIclPAoGAZqCd9CToMmhCKSvuChBB/vfArYw4XNrtW/Wb9Vy1d0haLQVWUYSst9suxjKU4QSS3P4UOWWFMZVzY4yfryej1iSnXwuscYN8nnFbZ7fsHq+hhMRqr/6bJnqVP2U30U44qV9wLmzGDYz0n9NsTR8ap4GPoI3dsye+U9gnKVsDkLs=
7b409284-9984-4a9d-9561-360855c9a641	2bc73ac6-1f42-43c1-9c37-7cd24fa76230	priority	100
509ad91c-cc4c-4a99-80fc-a4bfe85b318c	2bc73ac6-1f42-43c1-9c37-7cd24fa76230	certificate	MIICnTCCAYUCBgGZN8cW3TANBgkqhkiG9w0BAQsFADASMRAwDgYDVQQDDAdteXJlYWxtMB4XDTI1MDkxMTA3NTUxOFoXDTM1MDkxMTA3NTY1OFowEjEQMA4GA1UEAwwHbXlyZWFsbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKI76o9BkjNt69/3eixCiL7eaGg2qfk9dUyoJQ5zX4glRuGSAUGHOFy+zCk179slQg9g/Ntn0rVBucOV8wIpL4wGJTAw01ihgp8Vk2cqR8mq/TME5TolEmlJzV6zpQo2uJuSi5vRw1u80PTpmJ727s/m6g4FrOFmkDk1WKJpV+NnIi62XhTInEhWfxR3hJ11XxLsQi+CKaSQWMqDCzsLe1xQINC5P2o/F/1iKDM19ClQ6Ih6BhSr3jPZg09xmfuP8QvsRIyWiYty6SDO8GHtKDxwOOZ1WhV3e2jaSJwRpqOa4jboyuaf+gNJ/i8ZSt6mtYiAoZMqFSkKVJTT8i6GuxUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAQo0EL3ETgFGq38FSb+IKou2rRGbbkWkrr92V+I7T/tuUjiqGDph9R3iz27IJH+aYpW6wjSBHZkM3Tiv2WckFmiOgYYy0lJt+zfagquNOlXkryHifdx5FGTE26gWRz3ZeJWNEcj/3h1tOATRNqnDzur63l3odZXfhJd058KApeTG/UhmzG2Zl++sbhT8B3/bawpgZdZXyQcs1lV5fcL/Sw4quZYrW+DcTOVwzPwRIkZykcofT5RtTgIm5UBPCe4TxmvrYgjGBqMiKBnaDjbQHeZhRKEY6QFBxEyo672QL3QLr9WatJuC/XGibIJya1ySYY+a7aZ03c0+v2GOXM4fwuw==
673d132d-cf7f-4c16-bd15-821b31074455	bc98a599-6985-4b64-839d-de53979c73be	secret	NGKOudG3kCxhy_-lpXdDAAr8Mm8aiyRl_vqYdNNq1vsWeZdINXjEg9UH0mkFZhrt4HynueS1M5NJDsGc3B60BYWuM3HWSbjVd9EWRxdXXErRcV-zfSa3VivQWmtzvZ7PiKc4VMdnrKcZobKxwSeU30KSyxCrlpq0VqCFV1mouhc
649e05cd-6f11-4202-aa53-07fe8f5afdc4	bc98a599-6985-4b64-839d-de53979c73be	kid	af106b8c-62ca-4862-9b19-cde06272f3f7
347ceb6e-6eac-4a86-8ae6-5c6817780a81	bc98a599-6985-4b64-839d-de53979c73be	priority	100
3fc0013d-dd77-488e-9ea5-5b0ce84ff9e9	bc98a599-6985-4b64-839d-de53979c73be	algorithm	HS512
3f247d34-251d-4d8e-b06a-0a8b03951dad	a5d5b21d-0c83-4217-82e6-89c7fec3056d	client-uris-must-match	true
64e120f8-0899-4f19-b0d3-c5cfd675d531	a5d5b21d-0c83-4217-82e6-89c7fec3056d	host-sending-registration-request-must-match	true
1888af29-6d1a-4d68-b53f-9f63d815a9a3	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	oidc-full-name-mapper
6475fcb4-2fea-477c-a691-9b99f352ef40	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	saml-role-list-mapper
c69a1be4-441f-4a82-ace4-b34f0e9c2cbb	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	saml-user-attribute-mapper
1425f49b-18f8-4b0a-9d30-5cc5870152ee	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	oidc-address-mapper
4db9e094-b3ae-42b1-b64b-c02978b0d2f5	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
957cc977-9088-4f5e-aad9-8168568ab76c	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	saml-user-property-mapper
eaf24a84-fc5a-4e04-8baa-f4d98e2a3748	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
9951efbc-a8a1-42a4-969a-4c95d136bccd	da56dfd3-4d16-4e1d-b047-ada66713fc30	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
f59ce7ac-cabc-472e-9994-4372447f0730	8fd4d363-b69b-49c2-9db6-6e2448eb572f	max-clients	200
5ae59dae-d953-4f5e-aaac-e370188f8f16	715bdb05-35d8-4731-be23-a69fc11893f5	allow-default-scopes	true
5a43f25f-7994-4cf2-8711-863fb2067ff3	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
485e3ffa-e7d3-4235-b3aa-e559b32a6840	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	saml-role-list-mapper
b906ceac-e01b-414f-81c6-5054e1ee701b	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	saml-user-attribute-mapper
b6cf301b-b4cc-4eb4-ba97-418c4d811b32	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
3d6b026a-d46e-479f-a219-b9fde9fc69fa	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	saml-user-property-mapper
28f24dd6-317a-459e-815b-1ee539bff01e	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	oidc-address-mapper
8a225705-22bf-4496-9a2d-797194e4bef4	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
5bfaa124-ef12-47b0-b09d-4c723ae4d205	ecadf162-b455-42fd-b6cc-791134a91985	allowed-protocol-mapper-types	oidc-full-name-mapper
d44f967c-a6f0-42f2-9966-1bf82be680e8	cbd6316d-d76a-4e80-aa89-fb61bc55ea58	allow-default-scopes	true
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.composite_role (composite, child_role) FROM stdin;
656c3404-26ad-441b-a9eb-34a37249209c	262eb2b0-f1e7-4746-9f08-9d2c0fec90c6
656c3404-26ad-441b-a9eb-34a37249209c	f3c8a10b-0a15-4d49-8f15-7ef68d9c3b58
656c3404-26ad-441b-a9eb-34a37249209c	d154b224-6c8b-4889-9526-e896419de816
656c3404-26ad-441b-a9eb-34a37249209c	85bd74e7-436c-46b7-be85-74e60fd0ca35
656c3404-26ad-441b-a9eb-34a37249209c	680a535d-dd8b-4983-974b-cafa2a558a64
656c3404-26ad-441b-a9eb-34a37249209c	18a9293a-b964-4f2e-b5a8-ac9b8c07b472
656c3404-26ad-441b-a9eb-34a37249209c	5432e7a9-0adc-4621-8100-427510d2bee8
656c3404-26ad-441b-a9eb-34a37249209c	690d9c48-9642-47e8-963c-da7af1e78a4c
656c3404-26ad-441b-a9eb-34a37249209c	4b995b2f-a718-4cf9-b808-522da41c0795
656c3404-26ad-441b-a9eb-34a37249209c	b007896d-bb7d-4a0b-ac98-c1458702198e
656c3404-26ad-441b-a9eb-34a37249209c	fafab964-e391-424b-aff2-818bc909f55e
656c3404-26ad-441b-a9eb-34a37249209c	e54e1440-e77a-4ee7-8563-b90d46ec4bb2
656c3404-26ad-441b-a9eb-34a37249209c	bedde757-8850-4384-a408-06f14277e23f
656c3404-26ad-441b-a9eb-34a37249209c	223cf765-fbba-40b0-adc9-9c1873f00aea
656c3404-26ad-441b-a9eb-34a37249209c	ddeafb8c-08a5-4262-93a9-25fdcec7944c
656c3404-26ad-441b-a9eb-34a37249209c	e52a340a-3e5f-40c0-865e-9ecb565abc0a
656c3404-26ad-441b-a9eb-34a37249209c	6b40e58a-1f07-4c92-bd2f-83417dd818be
656c3404-26ad-441b-a9eb-34a37249209c	4d6af653-42f5-44f0-9e01-d70de27e6f07
4320f1ff-ff3b-4258-a106-af4e8c5c5357	3e90038c-07e7-4364-9341-b201e3c11b75
680a535d-dd8b-4983-974b-cafa2a558a64	e52a340a-3e5f-40c0-865e-9ecb565abc0a
85bd74e7-436c-46b7-be85-74e60fd0ca35	ddeafb8c-08a5-4262-93a9-25fdcec7944c
85bd74e7-436c-46b7-be85-74e60fd0ca35	4d6af653-42f5-44f0-9e01-d70de27e6f07
4320f1ff-ff3b-4258-a106-af4e8c5c5357	a9122d09-2de4-4652-9af3-30145f2b2b62
a9122d09-2de4-4652-9af3-30145f2b2b62	9b40a93f-614f-4bb9-a89d-c3388d86c6c6
73a34b37-f987-4ab6-b774-2fa34ef7eb85	581a6ae3-ccd7-443d-bd81-4a02c3a66186
656c3404-26ad-441b-a9eb-34a37249209c	4844dd2b-c6fb-47ce-9128-598cd86a2eeb
4320f1ff-ff3b-4258-a106-af4e8c5c5357	2149de2f-02f5-44f6-9ef2-c0e10e0a1d7e
4320f1ff-ff3b-4258-a106-af4e8c5c5357	a0c97752-c5f5-49c7-b652-1f4c1d9927d9
656c3404-26ad-441b-a9eb-34a37249209c	ed004721-eba6-4c82-ae65-7780b61a1115
656c3404-26ad-441b-a9eb-34a37249209c	af5fc65c-e227-43ac-a86c-d96d5c952611
656c3404-26ad-441b-a9eb-34a37249209c	4eadc48a-fcd2-451c-930b-db4cf8caa1b1
656c3404-26ad-441b-a9eb-34a37249209c	16954226-d131-48c0-bd3a-16767423a413
656c3404-26ad-441b-a9eb-34a37249209c	2f09dddf-0fb9-4d6a-9988-eaec5e0e4ba7
656c3404-26ad-441b-a9eb-34a37249209c	4cd768f4-13c4-45c9-b291-ab6321d2c4a8
656c3404-26ad-441b-a9eb-34a37249209c	b2802ec1-217a-424f-bd0b-1d3a653842ca
656c3404-26ad-441b-a9eb-34a37249209c	dcb9a52c-9482-452a-a536-1fb2dff364d5
656c3404-26ad-441b-a9eb-34a37249209c	0f40a726-7e9a-47b6-9b40-474915bc60b2
656c3404-26ad-441b-a9eb-34a37249209c	fccd9238-288f-4b83-9527-3fe06dae2159
656c3404-26ad-441b-a9eb-34a37249209c	0bc4deed-8616-4bf4-aad2-dbe4eb621f26
656c3404-26ad-441b-a9eb-34a37249209c	d93a5f25-6d9b-4ddc-8267-f3ffb890c5ea
656c3404-26ad-441b-a9eb-34a37249209c	d6b01b95-0191-4d33-ac94-716746ad585d
656c3404-26ad-441b-a9eb-34a37249209c	3bd99e25-72cd-4e57-b2b4-b016a1e34ec8
656c3404-26ad-441b-a9eb-34a37249209c	490d786f-afc3-42b5-b772-751cedf737b7
656c3404-26ad-441b-a9eb-34a37249209c	2cec4292-836a-434b-902c-428132057723
656c3404-26ad-441b-a9eb-34a37249209c	bddac2e9-f16b-4223-bdfa-d04c18fca304
16954226-d131-48c0-bd3a-16767423a413	490d786f-afc3-42b5-b772-751cedf737b7
4eadc48a-fcd2-451c-930b-db4cf8caa1b1	3bd99e25-72cd-4e57-b2b4-b016a1e34ec8
4eadc48a-fcd2-451c-930b-db4cf8caa1b1	bddac2e9-f16b-4223-bdfa-d04c18fca304
9e32735c-7562-4da0-b11a-25cb65885423	2573a4ea-cfb4-4fb9-9b05-0c3997bf2f6c
9e32735c-7562-4da0-b11a-25cb65885423	8c06e375-0002-4de4-b418-98df8c4d90d7
9e32735c-7562-4da0-b11a-25cb65885423	e16d119d-910d-48d1-aa51-e692c56cd7e0
9e32735c-7562-4da0-b11a-25cb65885423	127cda47-b2f4-4e2d-915f-0d4f8fba54b4
9e32735c-7562-4da0-b11a-25cb65885423	7abca80c-a820-40ce-a2e7-34de06114ccf
9e32735c-7562-4da0-b11a-25cb65885423	6baafada-c69b-4c21-8b2c-58f318850f17
9e32735c-7562-4da0-b11a-25cb65885423	bde0bc98-fdee-49c3-bfe8-d2e8890c14d0
9e32735c-7562-4da0-b11a-25cb65885423	51208541-6058-40f9-9b53-a0fdab693400
9e32735c-7562-4da0-b11a-25cb65885423	4ca15a16-9ca4-4d59-bf42-06da37ac5fa5
9e32735c-7562-4da0-b11a-25cb65885423	bc1d8a87-e5c2-430c-a687-519b638da872
9e32735c-7562-4da0-b11a-25cb65885423	c42336ee-bd54-4a9e-becf-9b084b96caeb
9e32735c-7562-4da0-b11a-25cb65885423	e99c1a49-60ae-4642-9c4e-1825965685fb
9e32735c-7562-4da0-b11a-25cb65885423	d8b43685-dc5b-476b-ad64-05a61f4e1a99
9e32735c-7562-4da0-b11a-25cb65885423	b07bfe9d-662e-4879-87c8-24b86b90339c
9e32735c-7562-4da0-b11a-25cb65885423	accc2ea9-624b-4542-bae9-2da1ac142053
9e32735c-7562-4da0-b11a-25cb65885423	d4c7f4a3-9f7f-4ebd-90e7-a67fbf81c8b1
9e32735c-7562-4da0-b11a-25cb65885423	9a00211b-59d9-4c22-84ed-ea1302bcf912
127cda47-b2f4-4e2d-915f-0d4f8fba54b4	accc2ea9-624b-4542-bae9-2da1ac142053
e16d119d-910d-48d1-aa51-e692c56cd7e0	9a00211b-59d9-4c22-84ed-ea1302bcf912
e16d119d-910d-48d1-aa51-e692c56cd7e0	b07bfe9d-662e-4879-87c8-24b86b90339c
e220c7fb-27f9-448e-842b-50ab8a4959ec	2a13f22b-6000-484c-bf3a-47a329bfaa54
e220c7fb-27f9-448e-842b-50ab8a4959ec	3899ca05-1d4f-450d-9b90-136174a882a4
3899ca05-1d4f-450d-9b90-136174a882a4	c5eb4c5a-78d5-46ec-b313-ddf49fa5bdbd
64a7d0cf-7b69-4cd9-8d64-eba2307b0511	a531e390-6bf4-4003-a902-0ece86a52b2b
656c3404-26ad-441b-a9eb-34a37249209c	2adb10b2-9528-488f-be89-7b0cf173bfcb
9e32735c-7562-4da0-b11a-25cb65885423	eb1b7f4c-acbb-40f9-9afd-ea096aac8260
e220c7fb-27f9-448e-842b-50ab8a4959ec	3e13666b-02f3-4571-b75b-1721edaf8537
e220c7fb-27f9-448e-842b-50ab8a4959ec	5b98e83e-e67a-4a69-893f-81d54620ad84
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority, version) FROM stdin;
b5c5f701-e814-4a39-a417-e9b0035bbeb1	\N	password	f6c10a19-f370-4d01-a6ae-a9e1477cb9fa	1757567872206	\N	{"value":"jajQtJiu8g0I44Xz45Z8rYRZE1OIZVx9h3yIUXXStSA=","salt":"k+BROuW7gOLEgZm3xmwdyg==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10	0
af3fb785-0d62-4e2a-910f-354b9c010eb9	\N	password	82a427d9-66af-477a-a670-f49fd33d50a4	1757838959585	My password	{"value":"BYwEJ/wlryM/FMWqA4AuZtObvVtCmvWK/fp2Oqcn+sw=","salt":"IZ0EBWDwxBIIOOrygZMAQg==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10	1
f2efa3bd-b0e5-4e9e-a949-d93cda728db8	\N	password	ae32774b-1bed-4ab5-afa0-1ee0295fb9eb	1757851748013	\N	{"value":"n05WsmM6ViyCXWABgGdGIQhGU0aLw4TbXhHStArBICQ=","salt":"+0uRyLELIyHAjCdw4Z8bXQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10	0
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2025-09-11 13:17:29.686211	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	7567848640
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2025-09-11 13:17:29.730308	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	7567848640
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2025-09-11 13:17:29.819243	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.29.1	\N	\N	7567848640
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2025-09-11 13:17:29.831599	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	7567848640
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2025-09-11 13:17:30.101943	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	7567848640
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2025-09-11 13:17:30.121976	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	7567848640
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2025-09-11 13:17:30.36029	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	7567848640
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2025-09-11 13:17:30.369948	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	7567848640
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2025-09-11 13:17:30.386813	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.29.1	\N	\N	7567848640
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2025-09-11 13:17:30.66367	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.29.1	\N	\N	7567848640
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2025-09-11 13:17:30.801307	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	7567848640
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2025-09-11 13:17:30.810308	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	7567848640
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2025-09-11 13:17:30.854503	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	7567848640
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-09-11 13:17:30.900916	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.29.1	\N	\N	7567848640
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-09-11 13:17:30.904133	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-09-11 13:17:30.909105	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.29.1	\N	\N	7567848640
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-09-11 13:17:30.91597	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.29.1	\N	\N	7567848640
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2025-09-11 13:17:31.066709	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.29.1	\N	\N	7567848640
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2025-09-11 13:17:31.185149	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	7567848640
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2025-09-11 13:17:31.194707	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	7567848640
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2025-09-11 13:17:31.200431	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	7567848640
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2025-09-11 13:17:31.206242	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	7567848640
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2025-09-11 13:17:31.371791	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.29.1	\N	\N	7567848640
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2025-09-11 13:17:31.382753	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	7567848640
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2025-09-11 13:17:31.384993	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	7567848640
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2025-09-11 13:17:32.201394	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.29.1	\N	\N	7567848640
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2025-09-11 13:17:32.393828	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.29.1	\N	\N	7567848640
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2025-09-11 13:17:32.400805	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	7567848640
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2025-09-11 13:17:32.564997	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.29.1	\N	\N	7567848640
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2025-09-11 13:17:32.598921	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.29.1	\N	\N	7567848640
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2025-09-11 13:17:32.654632	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.29.1	\N	\N	7567848640
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2025-09-11 13:17:32.666647	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.29.1	\N	\N	7567848640
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-09-11 13:17:32.682325	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-09-11 13:17:32.689118	34	MARK_RAN	9:f9753208029f582525ed12011a19d054	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	7567848640
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-09-11 13:17:32.788288	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	7567848640
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2025-09-11 13:17:32.801223	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.29.1	\N	\N	7567848640
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-09-11 13:17:32.814127	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	7567848640
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2025-09-11 13:17:32.824584	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.29.1	\N	\N	7567848640
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2025-09-11 13:17:32.83922	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.29.1	\N	\N	7567848640
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-09-11 13:17:32.846302	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	7567848640
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-09-11 13:17:32.851444	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	7567848640
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2025-09-11 13:17:32.868284	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.29.1	\N	\N	7567848640
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-09-11 13:17:36.118491	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.29.1	\N	\N	7567848640
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2025-09-11 13:17:36.133848	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.29.1	\N	\N	7567848640
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-09-11 13:17:36.152027	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	7567848640
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-09-11 13:17:36.182405	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.29.1	\N	\N	7567848640
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-09-11 13:17:36.187479	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	7567848640
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-09-11 13:17:36.605009	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.29.1	\N	\N	7567848640
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-09-11 13:17:36.61779	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.29.1	\N	\N	7567848640
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2025-09-11 13:17:36.72767	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.29.1	\N	\N	7567848640
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2025-09-11 13:17:37.919313	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.29.1	\N	\N	7567848640
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2025-09-11 13:17:37.925443	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2025-09-11 13:17:37.930002	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.29.1	\N	\N	7567848640
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2025-09-11 13:17:37.934191	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.29.1	\N	\N	7567848640
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-09-11 13:17:37.943644	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.29.1	\N	\N	7567848640
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-09-11 13:17:37.956078	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.29.1	\N	\N	7567848640
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-09-11 13:17:38.10488	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.29.1	\N	\N	7567848640
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-09-11 13:17:39.384548	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.29.1	\N	\N	7567848640
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2025-09-11 13:17:39.453528	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.29.1	\N	\N	7567848640
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2025-09-11 13:17:39.465708	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	7567848640
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-09-11 13:17:39.495291	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.29.1	\N	\N	7567848640
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-09-11 13:17:39.512076	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.29.1	\N	\N	7567848640
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2025-09-11 13:17:39.520419	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	7567848640
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2025-09-11 13:17:39.528998	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	7567848640
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2025-09-11 13:17:39.535932	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.29.1	\N	\N	7567848640
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2025-09-11 13:17:39.688332	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.29.1	\N	\N	7567848640
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2025-09-11 13:17:39.803533	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.29.1	\N	\N	7567848640
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2025-09-11 13:17:39.815718	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.29.1	\N	\N	7567848640
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2025-09-11 13:17:39.93975	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.29.1	\N	\N	7567848640
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2025-09-11 13:17:39.952226	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.29.1	\N	\N	7567848640
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2025-09-11 13:17:39.958983	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	7567848640
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-09-11 13:17:39.978087	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	7567848640
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-09-11 13:17:40.002999	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	7567848640
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-09-11 13:17:40.007993	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	7567848640
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-09-11 13:17:40.064644	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.29.1	\N	\N	7567848640
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-09-11 13:17:40.208876	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.29.1	\N	\N	7567848640
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-09-11 13:17:40.22484	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.29.1	\N	\N	7567848640
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-09-11 13:17:40.228732	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.29.1	\N	\N	7567848640
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-09-11 13:17:40.307925	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.29.1	\N	\N	7567848640
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-09-11 13:17:40.312624	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.29.1	\N	\N	7567848640
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-09-11 13:17:40.493709	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.29.1	\N	\N	7567848640
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-09-11 13:17:40.50298	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	7567848640
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-09-11 13:17:40.522506	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	7567848640
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-09-11 13:17:40.528594	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	7567848640
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-09-11 13:17:40.665178	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	7567848640
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2025-09-11 13:17:40.681056	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.29.1	\N	\N	7567848640
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-09-11 13:17:40.720846	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.29.1	\N	\N	7567848640
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-09-11 13:17:40.737943	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.29.1	\N	\N	7567848640
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.751659	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.29.1	\N	\N	7567848640
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.767721	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.29.1	\N	\N	7567848640
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.878623	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.910684	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.29.1	\N	\N	7567848640
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.91387	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	7567848640
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.931448	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	7567848640
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.93612	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.29.1	\N	\N	7567848640
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-09-11 13:17:40.949251	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.29.1	\N	\N	7567848640
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.321139	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.325503	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.362894	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.513359	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.519904	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.631878	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.29.1	\N	\N	7567848640
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-09-11 13:17:41.639133	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.29.1	\N	\N	7567848640
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2025-09-11 13:17:41.651863	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.29.1	\N	\N	7567848640
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2025-09-11 13:17:41.767418	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.29.1	\N	\N	7567848640
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2025-09-11 13:17:41.871793	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.29.1	\N	\N	7567848640
18.0.15-30992-index-consent	keycloak	META-INF/jpa-changelog-18.0.15.xml	2025-09-11 13:17:41.986194	107	EXECUTED	9:80071ede7a05604b1f4906f3bf3b00f0	createIndex indexName=IDX_USCONSENT_SCOPE_ID, tableName=USER_CONSENT_CLIENT_SCOPE		\N	4.29.1	\N	\N	7567848640
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2025-09-11 13:17:41.995269	108	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.29.1	\N	\N	7567848640
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-09-11 13:17:42.103478	109	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	7567848640
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-09-11 13:17:42.107956	110	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	7567848640
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-09-11 13:17:42.121835	111	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2025-09-11 13:17:42.129387	112	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.29.1	\N	\N	7567848640
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-09-11 13:17:42.164788	113	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	7567848640
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-09-11 13:17:42.170386	114	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.29.1	\N	\N	7567848640
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-09-11 13:17:42.180918	115	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.29.1	\N	\N	7567848640
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-09-11 13:17:42.184365	116	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.29.1	\N	\N	7567848640
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-09-11 13:17:42.201401	117	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.29.1	\N	\N	7567848640
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-09-11 13:17:42.212864	118	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	7567848640
24.0.0-9758	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-09-11 13:17:42.645487	119	EXECUTED	9:502c557a5189f600f0f445a9b49ebbce	addColumn tableName=USER_ATTRIBUTE; addColumn tableName=FED_USER_ATTRIBUTE; createIndex indexName=USER_ATTR_LONG_VALUES, tableName=USER_ATTRIBUTE; createIndex indexName=FED_USER_ATTR_LONG_VALUES, tableName=FED_USER_ATTRIBUTE; createIndex indexName...		\N	4.29.1	\N	\N	7567848640
24.0.0-9758-2	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-09-11 13:17:42.656095	120	EXECUTED	9:bf0fdee10afdf597a987adbf291db7b2	customChange		\N	4.29.1	\N	\N	7567848640
24.0.0-26618-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-09-11 13:17:42.669528	121	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
24.0.0-26618-reindex	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-09-11 13:17:42.781668	122	EXECUTED	9:08707c0f0db1cef6b352db03a60edc7f	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
24.0.2-27228	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-09-11 13:17:42.791666	123	EXECUTED	9:eaee11f6b8aa25d2cc6a84fb86fc6238	customChange		\N	4.29.1	\N	\N	7567848640
24.0.2-27967-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-09-11 13:17:42.797209	124	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
24.0.2-27967-reindex	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-09-11 13:17:42.804209	125	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-tables	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:42.819887	126	EXECUTED	9:deda2df035df23388af95bbd36c17cef	addColumn tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:42.911063	127	EXECUTED	9:3e96709818458ae49f3c679ae58d263a	createIndex indexName=IDX_OFFLINE_USS_BY_LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-cleanup-uss-createdon	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:43.327183	128	EXECUTED	9:78ab4fc129ed5e8265dbcc3485fba92f	dropIndex indexName=IDX_OFFLINE_USS_CREATEDON, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-cleanup-uss-preload	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:43.654473	129	EXECUTED	9:de5f7c1f7e10994ed8b62e621d20eaab	dropIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-cleanup-uss-by-usersess	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:43.919639	130	EXECUTED	9:6eee220d024e38e89c799417ec33667f	dropIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-cleanup-css-preload	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.183205	131	EXECUTED	9:5411d2fb2891d3e8d63ddb55dfa3c0c9	dropIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-2-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.185731	132	MARK_RAN	9:b7ef76036d3126bb83c2423bf4d449d6	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-28265-index-2-not-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.257561	133	EXECUTED	9:23396cf51ab8bc1ae6f0cac7f9f6fcf7	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	7567848640
25.0.0-org	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.279479	134	EXECUTED	9:5c859965c2c9b9c72136c360649af157	createTable tableName=ORG; addUniqueConstraint constraintName=UK_ORG_NAME, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_GROUP, tableName=ORG; createTable tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	7567848640
unique-consentuser	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.297234	135	EXECUTED	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	7567848640
unique-consentuser-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.300145	136	MARK_RAN	9:b79478aad5adaa1bc428e31563f55e8e	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	7567848640
25.0.0-28861-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-09-11 13:17:44.482737	137	EXECUTED	9:b9acb58ac958d9ada0fe12a5d4794ab1	createIndex indexName=IDX_PERM_TICKET_REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; createIndex indexName=IDX_PERM_TICKET_OWNER, tableName=RESOURCE_SERVER_PERM_TICKET		\N	4.29.1	\N	\N	7567848640
26.0.0-org-alias	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.496298	138	EXECUTED	9:6ef7d63e4412b3c2d66ed179159886a4	addColumn tableName=ORG; update tableName=ORG; addNotNullConstraint columnName=ALIAS, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_ALIAS, tableName=ORG		\N	4.29.1	\N	\N	7567848640
26.0.0-org-group	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.514496	139	EXECUTED	9:da8e8087d80ef2ace4f89d8c5b9ca223	addColumn tableName=KEYCLOAK_GROUP; update tableName=KEYCLOAK_GROUP; addNotNullConstraint columnName=TYPE, tableName=KEYCLOAK_GROUP; customChange		\N	4.29.1	\N	\N	7567848640
26.0.0-org-indexes	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.604357	140	EXECUTED	9:79b05dcd610a8c7f25ec05135eec0857	createIndex indexName=IDX_ORG_DOMAIN_ORG_ID, tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	7567848640
26.0.0-org-group-membership	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.613961	141	EXECUTED	9:a6ace2ce583a421d89b01ba2a28dc2d4	addColumn tableName=USER_GROUP_MEMBERSHIP; update tableName=USER_GROUP_MEMBERSHIP; addNotNullConstraint columnName=MEMBERSHIP_TYPE, tableName=USER_GROUP_MEMBERSHIP		\N	4.29.1	\N	\N	7567848640
31296-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.629765	142	EXECUTED	9:64ef94489d42a358e8304b0e245f0ed4	createTable tableName=REVOKED_TOKEN; addPrimaryKey constraintName=CONSTRAINT_RT, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	7567848640
31725-index-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.731548	143	EXECUTED	9:b994246ec2bf7c94da881e1d28782c7b	createIndex indexName=IDX_REV_TOKEN_ON_EXPIRE, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	7567848640
26.0.0-idps-for-login	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:44.959188	144	EXECUTED	9:51f5fffadf986983d4bd59582c6c1604	addColumn tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_REALM_ORG, tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_FOR_LOGIN, tableName=IDENTITY_PROVIDER; customChange		\N	4.29.1	\N	\N	7567848640
26.0.0-32583-drop-redundant-index-on-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:45.343514	145	EXECUTED	9:24972d83bf27317a055d234187bb4af9	dropIndex indexName=IDX_US_SESS_ID_ON_CL_SESS, tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	7567848640
26.0.0.32582-remove-tables-user-session-user-session-note-and-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:45.386245	146	EXECUTED	9:febdc0f47f2ed241c59e60f58c3ceea5	dropTable tableName=CLIENT_SESSION_ROLE; dropTable tableName=CLIENT_SESSION_NOTE; dropTable tableName=CLIENT_SESSION_PROT_MAPPER; dropTable tableName=CLIENT_SESSION_AUTH_STATUS; dropTable tableName=CLIENT_USER_SESSION_NOTE; dropTable tableName=CLI...		\N	4.29.1	\N	\N	7567848640
26.0.0-33201-org-redirect-url	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-09-11 13:17:45.393725	147	EXECUTED	9:4d0e22b0ac68ebe9794fa9cb752ea660	addColumn tableName=ORG		\N	4.29.1	\N	\N	7567848640
29399-jdbc-ping-default	keycloak	META-INF/jpa-changelog-26.1.0.xml	2025-09-11 13:17:45.416686	148	EXECUTED	9:007dbe99d7203fca403b89d4edfdf21e	createTable tableName=JGROUPS_PING; addPrimaryKey constraintName=CONSTRAINT_JGROUPS_PING, tableName=JGROUPS_PING		\N	4.29.1	\N	\N	7567848640
26.1.0-34013	keycloak	META-INF/jpa-changelog-26.1.0.xml	2025-09-11 13:17:45.441671	149	EXECUTED	9:e6b686a15759aef99a6d758a5c4c6a26	addColumn tableName=ADMIN_EVENT_ENTITY		\N	4.29.1	\N	\N	7567848640
26.1.0-34380	keycloak	META-INF/jpa-changelog-26.1.0.xml	2025-09-11 13:17:45.45387	150	EXECUTED	9:ac8b9edb7c2b6c17a1c7a11fcf5ccf01	dropTable tableName=USERNAME_LOGIN_FAILURE		\N	4.29.1	\N	\N	7567848640
26.2.0-36750	keycloak	META-INF/jpa-changelog-26.2.0.xml	2025-09-11 13:17:45.472231	151	EXECUTED	9:b49ce951c22f7eb16480ff085640a33a	createTable tableName=SERVER_CONFIG		\N	4.29.1	\N	\N	7567848640
26.2.0-26106	keycloak	META-INF/jpa-changelog-26.2.0.xml	2025-09-11 13:17:45.480049	152	EXECUTED	9:b5877d5dab7d10ff3a9d209d7beb6680	addColumn tableName=CREDENTIAL		\N	4.29.1	\N	\N	7567848640
26.2.6-39866-duplicate	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-09-11 13:17:45.487768	153	EXECUTED	9:1dc67ccee24f30331db2cba4f372e40e	customChange		\N	4.29.1	\N	\N	7567848640
26.2.6-39866-uk	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-09-11 13:17:45.498784	154	EXECUTED	9:b70b76f47210cf0a5f4ef0e219eac7cd	addUniqueConstraint constraintName=UK_MIGRATION_VERSION, tableName=MIGRATION_MODEL		\N	4.29.1	\N	\N	7567848640
26.2.6-40088-duplicate	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-09-11 13:17:45.510588	155	EXECUTED	9:cc7e02ed69ab31979afb1982f9670e8f	customChange		\N	4.29.1	\N	\N	7567848640
26.2.6-40088-uk	keycloak	META-INF/jpa-changelog-26.2.6.xml	2025-09-11 13:17:45.522408	156	EXECUTED	9:5bb848128da7bc4595cc507383325241	addUniqueConstraint constraintName=UK_MIGRATION_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.29.1	\N	\N	7567848640
26.3.0-groups-description	keycloak	META-INF/jpa-changelog-26.3.0.xml	2025-09-11 13:17:45.539057	157	EXECUTED	9:e1a3c05574326fb5b246b73b9a4c4d49	addColumn tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	7567848640
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
74fb1520-0e85-4576-bac4-f72784d7f550	f4091e6d-5fe5-4fd0-b0fc-2db39712fe65	f
74fb1520-0e85-4576-bac4-f72784d7f550	33013151-6388-4ab7-8d48-faab9170c32d	t
74fb1520-0e85-4576-bac4-f72784d7f550	b921d787-c0f9-4120-95b1-86e6e44692b1	t
74fb1520-0e85-4576-bac4-f72784d7f550	4c8704d3-38e6-4ae0-822c-86a4dade868f	t
74fb1520-0e85-4576-bac4-f72784d7f550	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41	t
74fb1520-0e85-4576-bac4-f72784d7f550	042b74c6-0912-4c82-a237-24c8ce2b3bd1	f
74fb1520-0e85-4576-bac4-f72784d7f550	7df7118b-d597-49fa-90bf-1bb838fb8175	f
74fb1520-0e85-4576-bac4-f72784d7f550	c67627c2-9400-4e49-9dd5-c2e63ffecf41	t
74fb1520-0e85-4576-bac4-f72784d7f550	9f109951-4e26-4cdf-85f5-573304a0beae	t
74fb1520-0e85-4576-bac4-f72784d7f550	4ab74077-74dc-42a3-870d-a817e982b809	f
74fb1520-0e85-4576-bac4-f72784d7f550	2970ae3c-c362-43a4-98c2-69ec6751a4fb	t
74fb1520-0e85-4576-bac4-f72784d7f550	8103e232-f3e5-4ae8-98ea-090d19460659	t
74fb1520-0e85-4576-bac4-f72784d7f550	eede082d-086f-46ca-901b-e2ab7fcc32bc	f
c09c14f3-a5f7-4baa-be03-70f28dad6f95	053227ab-d986-433c-af64-8b27c9f0a969	f
c09c14f3-a5f7-4baa-be03-70f28dad6f95	94b1c360-5621-48e2-aad5-ea6c2e3db50e	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	432d2b41-1130-4dbe-8f80-58a226978e9b	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	fbcc6996-fb96-41b0-ae93-b975961a4e48	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	3f38285d-7251-42f8-bc67-0a9ab4db468d	f
c09c14f3-a5f7-4baa-be03-70f28dad6f95	7f670e6a-ed04-40e6-bd85-91dc7f2ded26	f
c09c14f3-a5f7-4baa-be03-70f28dad6f95	4a34f71d-f92a-4c2e-88a3-004069c2c6da	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	d0c91b1e-9eeb-486d-97c3-8e5777998483	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	32f1d062-4616-484d-a701-0baa63bd331a	f
c09c14f3-a5f7-4baa-be03-70f28dad6f95	35e67b27-237a-4cfb-ac05-bf478f6012c5	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1	t
c09c14f3-a5f7-4baa-be03-70f28dad6f95	d83fef10-7f01-4b9d-ac7d-c242bfd1b584	f
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id, details_json_long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only, organization_id, hide_on_login) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: jgroups_ping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jgroups_ping (address, name, cluster_name, ip, coord) FROM stdin;
uuid://00000000-0000-0000-0000-000000000001	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000002	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000003	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000004	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000005	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000006	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000007	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000008	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-000000000009	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-00000000000a	(starting)	ISPN	127.0.0.1:0	f
uuid://00000000-0000-0000-0000-00000000000b	(starting)	ISPN	127.0.0.1:0	f
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.keycloak_group (id, name, parent_group, realm_id, type, description) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
4320f1ff-ff3b-4258-a106-af4e8c5c5357	74fb1520-0e85-4576-bac4-f72784d7f550	f	${role_default-roles}	default-roles-master	74fb1520-0e85-4576-bac4-f72784d7f550	\N	\N
262eb2b0-f1e7-4746-9f08-9d2c0fec90c6	74fb1520-0e85-4576-bac4-f72784d7f550	f	${role_create-realm}	create-realm	74fb1520-0e85-4576-bac4-f72784d7f550	\N	\N
656c3404-26ad-441b-a9eb-34a37249209c	74fb1520-0e85-4576-bac4-f72784d7f550	f	${role_admin}	admin	74fb1520-0e85-4576-bac4-f72784d7f550	\N	\N
f3c8a10b-0a15-4d49-8f15-7ef68d9c3b58	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_create-client}	create-client	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
d154b224-6c8b-4889-9526-e896419de816	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_view-realm}	view-realm	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
85bd74e7-436c-46b7-be85-74e60fd0ca35	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_view-users}	view-users	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
680a535d-dd8b-4983-974b-cafa2a558a64	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_view-clients}	view-clients	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
18a9293a-b964-4f2e-b5a8-ac9b8c07b472	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_view-events}	view-events	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
5432e7a9-0adc-4621-8100-427510d2bee8	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_view-identity-providers}	view-identity-providers	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
690d9c48-9642-47e8-963c-da7af1e78a4c	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_view-authorization}	view-authorization	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
4b995b2f-a718-4cf9-b808-522da41c0795	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_manage-realm}	manage-realm	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
b007896d-bb7d-4a0b-ac98-c1458702198e	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_manage-users}	manage-users	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
fafab964-e391-424b-aff2-818bc909f55e	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_manage-clients}	manage-clients	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
e54e1440-e77a-4ee7-8563-b90d46ec4bb2	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_manage-events}	manage-events	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
bedde757-8850-4384-a408-06f14277e23f	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_manage-identity-providers}	manage-identity-providers	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
223cf765-fbba-40b0-adc9-9c1873f00aea	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_manage-authorization}	manage-authorization	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
ddeafb8c-08a5-4262-93a9-25fdcec7944c	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_query-users}	query-users	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
e52a340a-3e5f-40c0-865e-9ecb565abc0a	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_query-clients}	query-clients	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
6b40e58a-1f07-4c92-bd2f-83417dd818be	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_query-realms}	query-realms	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
4d6af653-42f5-44f0-9e01-d70de27e6f07	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_query-groups}	query-groups	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
3e90038c-07e7-4364-9341-b201e3c11b75	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_view-profile}	view-profile	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
a9122d09-2de4-4652-9af3-30145f2b2b62	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_manage-account}	manage-account	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
9b40a93f-614f-4bb9-a89d-c3388d86c6c6	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_manage-account-links}	manage-account-links	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
df9a5ec6-d897-41b4-a8cf-0b5ee3ed1a23	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_view-applications}	view-applications	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
581a6ae3-ccd7-443d-bd81-4a02c3a66186	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_view-consent}	view-consent	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
73a34b37-f987-4ab6-b774-2fa34ef7eb85	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_manage-consent}	manage-consent	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
54765c2f-0f6e-4a28-9093-b68a4d6f81ad	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_view-groups}	view-groups	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
6ad66437-833f-4df2-bf6c-a4eb1dd55d1c	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	t	${role_delete-account}	delete-account	74fb1520-0e85-4576-bac4-f72784d7f550	1ba364ae-bf5f-4e45-8ef7-59edee20d88c	\N
4b43aba0-68d8-44dd-a244-b7bf258bd7d3	2e747951-38af-47c9-b220-67263b5151b8	t	${role_read-token}	read-token	74fb1520-0e85-4576-bac4-f72784d7f550	2e747951-38af-47c9-b220-67263b5151b8	\N
4844dd2b-c6fb-47ce-9128-598cd86a2eeb	41fc49ae-52ee-4330-a1a1-8af3802bc5af	t	${role_impersonation}	impersonation	74fb1520-0e85-4576-bac4-f72784d7f550	41fc49ae-52ee-4330-a1a1-8af3802bc5af	\N
2149de2f-02f5-44f6-9ef2-c0e10e0a1d7e	74fb1520-0e85-4576-bac4-f72784d7f550	f	${role_offline-access}	offline_access	74fb1520-0e85-4576-bac4-f72784d7f550	\N	\N
a0c97752-c5f5-49c7-b652-1f4c1d9927d9	74fb1520-0e85-4576-bac4-f72784d7f550	f	${role_uma_authorization}	uma_authorization	74fb1520-0e85-4576-bac4-f72784d7f550	\N	\N
e220c7fb-27f9-448e-842b-50ab8a4959ec	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f	${role_default-roles}	default-roles-myrealm	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N	\N
ed004721-eba6-4c82-ae65-7780b61a1115	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_create-client}	create-client	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
af5fc65c-e227-43ac-a86c-d96d5c952611	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_view-realm}	view-realm	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
4eadc48a-fcd2-451c-930b-db4cf8caa1b1	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_view-users}	view-users	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
16954226-d131-48c0-bd3a-16767423a413	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_view-clients}	view-clients	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
2f09dddf-0fb9-4d6a-9988-eaec5e0e4ba7	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_view-events}	view-events	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
4cd768f4-13c4-45c9-b291-ab6321d2c4a8	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_view-identity-providers}	view-identity-providers	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
b2802ec1-217a-424f-bd0b-1d3a653842ca	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_view-authorization}	view-authorization	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
dcb9a52c-9482-452a-a536-1fb2dff364d5	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_manage-realm}	manage-realm	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
0f40a726-7e9a-47b6-9b40-474915bc60b2	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_manage-users}	manage-users	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
fccd9238-288f-4b83-9527-3fe06dae2159	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_manage-clients}	manage-clients	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
0bc4deed-8616-4bf4-aad2-dbe4eb621f26	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_manage-events}	manage-events	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
d93a5f25-6d9b-4ddc-8267-f3ffb890c5ea	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_manage-identity-providers}	manage-identity-providers	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
d6b01b95-0191-4d33-ac94-716746ad585d	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_manage-authorization}	manage-authorization	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
3bd99e25-72cd-4e57-b2b4-b016a1e34ec8	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_query-users}	query-users	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
490d786f-afc3-42b5-b772-751cedf737b7	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_query-clients}	query-clients	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
2cec4292-836a-434b-902c-428132057723	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_query-realms}	query-realms	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
bddac2e9-f16b-4223-bdfa-d04c18fca304	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_query-groups}	query-groups	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
9e32735c-7562-4da0-b11a-25cb65885423	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_realm-admin}	realm-admin	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
2573a4ea-cfb4-4fb9-9b05-0c3997bf2f6c	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_create-client}	create-client	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
8c06e375-0002-4de4-b418-98df8c4d90d7	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_view-realm}	view-realm	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
e16d119d-910d-48d1-aa51-e692c56cd7e0	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_view-users}	view-users	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
127cda47-b2f4-4e2d-915f-0d4f8fba54b4	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_view-clients}	view-clients	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
7abca80c-a820-40ce-a2e7-34de06114ccf	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_view-events}	view-events	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
6baafada-c69b-4c21-8b2c-58f318850f17	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_view-identity-providers}	view-identity-providers	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
bde0bc98-fdee-49c3-bfe8-d2e8890c14d0	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_view-authorization}	view-authorization	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
51208541-6058-40f9-9b53-a0fdab693400	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_manage-realm}	manage-realm	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
4ca15a16-9ca4-4d59-bf42-06da37ac5fa5	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_manage-users}	manage-users	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
bc1d8a87-e5c2-430c-a687-519b638da872	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_manage-clients}	manage-clients	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
c42336ee-bd54-4a9e-becf-9b084b96caeb	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_manage-events}	manage-events	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
e99c1a49-60ae-4642-9c4e-1825965685fb	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_manage-identity-providers}	manage-identity-providers	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
d8b43685-dc5b-476b-ad64-05a61f4e1a99	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_manage-authorization}	manage-authorization	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
b07bfe9d-662e-4879-87c8-24b86b90339c	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_query-users}	query-users	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
accc2ea9-624b-4542-bae9-2da1ac142053	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_query-clients}	query-clients	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
d4c7f4a3-9f7f-4ebd-90e7-a67fbf81c8b1	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_query-realms}	query-realms	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
9a00211b-59d9-4c22-84ed-ea1302bcf912	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_query-groups}	query-groups	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
2a13f22b-6000-484c-bf3a-47a329bfaa54	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_view-profile}	view-profile	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
3899ca05-1d4f-450d-9b90-136174a882a4	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_manage-account}	manage-account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
c5eb4c5a-78d5-46ec-b313-ddf49fa5bdbd	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_manage-account-links}	manage-account-links	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
e5372aa0-539b-4cf7-bec8-cf4f29c5e79b	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_view-applications}	view-applications	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
a531e390-6bf4-4003-a902-0ece86a52b2b	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_view-consent}	view-consent	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
64a7d0cf-7b69-4cd9-8d64-eba2307b0511	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_manage-consent}	manage-consent	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
58b3cee6-b755-417c-9846-1c5d304916ba	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_view-groups}	view-groups	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
375b290e-9e0e-48b8-a943-20cf7f7e0053	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	t	${role_delete-account}	delete-account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	\N
2adb10b2-9528-488f-be89-7b0cf173bfcb	2477d9ab-3104-4cca-baaf-650e8260bccd	t	${role_impersonation}	impersonation	74fb1520-0e85-4576-bac4-f72784d7f550	2477d9ab-3104-4cca-baaf-650e8260bccd	\N
eb1b7f4c-acbb-40f9-9afd-ea096aac8260	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	t	${role_impersonation}	impersonation	c09c14f3-a5f7-4baa-be03-70f28dad6f95	006e11c5-ca8f-43ec-a96a-f49a9b27d8d6	\N
945196e2-1a8e-47bb-8946-dc55d9ba5ce2	2e851cb4-3bb2-435a-98d5-2f052922f192	t	${role_read-token}	read-token	c09c14f3-a5f7-4baa-be03-70f28dad6f95	2e851cb4-3bb2-435a-98d5-2f052922f192	\N
3e13666b-02f3-4571-b75b-1721edaf8537	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f	${role_offline-access}	offline_access	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N	\N
5b98e83e-e67a-4a69-893f-81d54620ad84	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f	${role_uma_authorization}	uma_authorization	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N	\N
fbe2ea14-3e6c-4e30-9242-28a72b0a7d52	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f		CREATE_ACCOUNT	c09c14f3-a5f7-4baa-be03-70f28dad6f95	\N	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.migration_model (id, version, update_time) FROM stdin;
ozrov	26.3.3	1757567868
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id, version) FROM stdin;
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh, broker_session_id, version) FROM stdin;
\.


--
-- Data for Name: org; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.org (id, enabled, realm_id, group_id, name, description, alias, redirect_url) FROM stdin;
\.


--
-- Data for Name: org_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.org_domain (id, name, verified, org_id) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
b34a1af7-0d87-4f38-b35d-db094fd4ae65	audience resolve	openid-connect	oidc-audience-resolve-mapper	45d84e44-7b18-42db-acb0-3deaf693558b	\N
581efa6e-cba6-4114-9c05-8f16e5b08113	locale	openid-connect	oidc-usermodel-attribute-mapper	dd90a450-d5ef-4d13-b211-5a794446a37f	\N
8350d0a8-44f5-4d3d-8ee7-762e97ac9261	role list	saml	saml-role-list-mapper	\N	33013151-6388-4ab7-8d48-faab9170c32d
59ea0cea-e4f7-42dd-bab8-52cbf769883d	organization	saml	saml-organization-membership-mapper	\N	b921d787-c0f9-4120-95b1-86e6e44692b1
69fb5cbb-25d4-433c-8c8c-84b3210d32cf	full name	openid-connect	oidc-full-name-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
126eadc5-a192-4027-8d70-5b9f6d01275a	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
ea9ab90b-f912-4b3a-954b-4cea951181f5	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
20155d21-aec0-442e-a81c-682262ef64de	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	username	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
e46437bb-abee-49f4-845e-3f03b40646e2	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	website	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
706031fc-e69b-4b30-a2cd-d86586704e5a	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
a2b1a04e-e131-4466-ab27-254d094f4710	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
42436cbd-342b-4828-8cff-2845d949eeb0	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
6294c58a-e61f-4b45-9fb0-2609af530621	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	4c8704d3-38e6-4ae0-822c-86a4dade868f
949d7607-46b2-4d18-af0c-240ca9a203d4	email	openid-connect	oidc-usermodel-attribute-mapper	\N	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41
739bb76b-1aa0-42ea-aa65-b73359da0f3b	email verified	openid-connect	oidc-usermodel-property-mapper	\N	6c8ec5d7-8c5b-4f37-b9fa-61081a594c41
67c4a03e-5824-4e21-bdae-25eed9d85d51	address	openid-connect	oidc-address-mapper	\N	042b74c6-0912-4c82-a237-24c8ce2b3bd1
c4551015-57d0-4f0e-9363-dfbd742bc14d	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	7df7118b-d597-49fa-90bf-1bb838fb8175
73ac7930-80cf-478a-aae1-18d5bc1305d3	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	7df7118b-d597-49fa-90bf-1bb838fb8175
c0fed595-d503-4711-8f47-6ff0f70e6def	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	c67627c2-9400-4e49-9dd5-c2e63ffecf41
00c9d799-93aa-41fe-b6e7-44471d15d9fe	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	c67627c2-9400-4e49-9dd5-c2e63ffecf41
6655453e-abe3-4a7f-9117-43945e99b924	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	c67627c2-9400-4e49-9dd5-c2e63ffecf41
0486b434-31c6-4e6c-919d-7b9c41d1716a	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	9f109951-4e26-4cdf-85f5-573304a0beae
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	4ab74077-74dc-42a3-870d-a817e982b809
cfd63a00-31c5-47df-b15a-cddf49ab24c0	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	4ab74077-74dc-42a3-870d-a817e982b809
354202ba-75e8-4b0d-8a2e-ca5aa0eff427	acr loa level	openid-connect	oidc-acr-mapper	\N	2970ae3c-c362-43a4-98c2-69ec6751a4fb
141994da-3ae6-4e92-bea0-2deecf060ced	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	8103e232-f3e5-4ae8-98ea-090d19460659
7569f991-5b47-4eb4-9abf-bc309f9b4f38	sub	openid-connect	oidc-sub-mapper	\N	8103e232-f3e5-4ae8-98ea-090d19460659
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	\N	c4f3456a-56fe-45d1-9a58-0cb13c1c5aae
556d64a7-2931-44e0-8d34-42b998703b2b	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	\N	c4f3456a-56fe-45d1-9a58-0cb13c1c5aae
472eda5c-ecb6-48f2-adde-6e750b3b47cb	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	\N	c4f3456a-56fe-45d1-9a58-0cb13c1c5aae
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	organization	openid-connect	oidc-organization-membership-mapper	\N	eede082d-086f-46ca-901b-e2ab7fcc32bc
e1cf9e9f-427a-4778-bbd9-0d48e60dba24	audience resolve	openid-connect	oidc-audience-resolve-mapper	c4ac8131-45b7-428a-b2bc-0966287ddf96	\N
dee179e9-3252-4fb5-9794-183b5dad20d0	role list	saml	saml-role-list-mapper	\N	94b1c360-5621-48e2-aad5-ea6c2e3db50e
5a6e49d6-3526-4396-92c8-5cbdcc49580d	organization	saml	saml-organization-membership-mapper	\N	432d2b41-1130-4dbe-8f80-58a226978e9b
c0333e50-9a17-4c6c-be7d-535f327d3ed7	full name	openid-connect	oidc-full-name-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
eb6d7510-51c6-4335-a558-b74f9e2d67cd	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
6bfe8fef-9b62-41be-b068-f58851dfad46	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
75d66380-431e-42dc-8d00-3aa291e23afa	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
ed45f755-b43b-4a99-8d9d-9699e6515dbe	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	username	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
0848c473-baa9-4805-9c57-8d212305fd56	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
45e8e325-1ab7-4599-83c3-16a6b54abb78	website	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
9a77bab1-b85c-4959-9379-9caaf48282a6	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
fd209626-8cbe-4ef1-aed0-b4084ba953ba	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
8615514f-69fa-44ce-930d-356b7a632362	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
baf54263-3389-4b97-a367-f6b2797e4634	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	a4ca1082-704a-46e3-9ea8-09cb1a9a9c21
e10a0c93-fd67-4ca6-a437-740be926a4c2	email	openid-connect	oidc-usermodel-attribute-mapper	\N	fbcc6996-fb96-41b0-ae93-b975961a4e48
997047e9-278f-4ea3-9d21-d0b725280084	email verified	openid-connect	oidc-usermodel-property-mapper	\N	fbcc6996-fb96-41b0-ae93-b975961a4e48
84e3e0d6-8829-424e-bd53-ff357f4c32f7	address	openid-connect	oidc-address-mapper	\N	3f38285d-7251-42f8-bc67-0a9ab4db468d
534df5f1-bd3b-4f09-87b0-b55da2b4be78	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	7f670e6a-ed04-40e6-bd85-91dc7f2ded26
838e3888-0136-4edf-9e6a-f892ea37e40d	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	7f670e6a-ed04-40e6-bd85-91dc7f2ded26
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	4a34f71d-f92a-4c2e-88a3-004069c2c6da
899f2c62-8e34-43d3-a692-50c3d28c6977	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	4a34f71d-f92a-4c2e-88a3-004069c2c6da
686a1a10-24be-4440-9360-363994400628	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	4a34f71d-f92a-4c2e-88a3-004069c2c6da
7b4377be-1918-4443-9497-11c51c2ce608	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	d0c91b1e-9eeb-486d-97c3-8e5777998483
730bdbfe-a842-40ad-8a0f-35ce66c597f1	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	32f1d062-4616-484d-a701-0baa63bd331a
eb677d4a-2711-49e4-88c7-af045dca05a3	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	32f1d062-4616-484d-a701-0baa63bd331a
2a7ed9e2-5107-48c5-9ffa-ee3ad04ee609	acr loa level	openid-connect	oidc-acr-mapper	\N	35e67b27-237a-4cfb-ac05-bf478f6012c5
99b7c2a3-0075-4c07-aef6-de377a977609	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1
007dd1f1-6a55-4917-b7b3-0900d2979346	sub	openid-connect	oidc-sub-mapper	\N	1a05cac7-a20a-4ef3-81bc-47a487e7c1b1
5afddf16-9606-4c94-ac67-3b1f89db5774	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	\N	c4ee7cf3-4427-4b56-971d-c1c52bcd71f3
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	\N	c4ee7cf3-4427-4b56-971d-c1c52bcd71f3
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	\N	c4ee7cf3-4427-4b56-971d-c1c52bcd71f3
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	organization	openid-connect	oidc-organization-membership-mapper	\N	d83fef10-7f01-4b9d-ac7d-c242bfd1b584
5ac106a3-6077-486f-a3ec-f1b5188cee8d	locale	openid-connect	oidc-usermodel-attribute-mapper	82806441-9195-44ea-a4e7-cbeb15c17556	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
581efa6e-cba6-4114-9c05-8f16e5b08113	true	introspection.token.claim
581efa6e-cba6-4114-9c05-8f16e5b08113	true	userinfo.token.claim
581efa6e-cba6-4114-9c05-8f16e5b08113	locale	user.attribute
581efa6e-cba6-4114-9c05-8f16e5b08113	true	id.token.claim
581efa6e-cba6-4114-9c05-8f16e5b08113	true	access.token.claim
581efa6e-cba6-4114-9c05-8f16e5b08113	locale	claim.name
581efa6e-cba6-4114-9c05-8f16e5b08113	String	jsonType.label
8350d0a8-44f5-4d3d-8ee7-762e97ac9261	false	single
8350d0a8-44f5-4d3d-8ee7-762e97ac9261	Basic	attribute.nameformat
8350d0a8-44f5-4d3d-8ee7-762e97ac9261	Role	attribute.name
126eadc5-a192-4027-8d70-5b9f6d01275a	true	introspection.token.claim
126eadc5-a192-4027-8d70-5b9f6d01275a	true	userinfo.token.claim
126eadc5-a192-4027-8d70-5b9f6d01275a	lastName	user.attribute
126eadc5-a192-4027-8d70-5b9f6d01275a	true	id.token.claim
126eadc5-a192-4027-8d70-5b9f6d01275a	true	access.token.claim
126eadc5-a192-4027-8d70-5b9f6d01275a	family_name	claim.name
126eadc5-a192-4027-8d70-5b9f6d01275a	String	jsonType.label
20155d21-aec0-442e-a81c-682262ef64de	true	introspection.token.claim
20155d21-aec0-442e-a81c-682262ef64de	true	userinfo.token.claim
20155d21-aec0-442e-a81c-682262ef64de	nickname	user.attribute
20155d21-aec0-442e-a81c-682262ef64de	true	id.token.claim
20155d21-aec0-442e-a81c-682262ef64de	true	access.token.claim
20155d21-aec0-442e-a81c-682262ef64de	nickname	claim.name
20155d21-aec0-442e-a81c-682262ef64de	String	jsonType.label
42436cbd-342b-4828-8cff-2845d949eeb0	true	introspection.token.claim
42436cbd-342b-4828-8cff-2845d949eeb0	true	userinfo.token.claim
42436cbd-342b-4828-8cff-2845d949eeb0	locale	user.attribute
42436cbd-342b-4828-8cff-2845d949eeb0	true	id.token.claim
42436cbd-342b-4828-8cff-2845d949eeb0	true	access.token.claim
42436cbd-342b-4828-8cff-2845d949eeb0	locale	claim.name
42436cbd-342b-4828-8cff-2845d949eeb0	String	jsonType.label
6294c58a-e61f-4b45-9fb0-2609af530621	true	introspection.token.claim
6294c58a-e61f-4b45-9fb0-2609af530621	true	userinfo.token.claim
6294c58a-e61f-4b45-9fb0-2609af530621	updatedAt	user.attribute
6294c58a-e61f-4b45-9fb0-2609af530621	true	id.token.claim
6294c58a-e61f-4b45-9fb0-2609af530621	true	access.token.claim
6294c58a-e61f-4b45-9fb0-2609af530621	updated_at	claim.name
6294c58a-e61f-4b45-9fb0-2609af530621	long	jsonType.label
69fb5cbb-25d4-433c-8c8c-84b3210d32cf	true	introspection.token.claim
69fb5cbb-25d4-433c-8c8c-84b3210d32cf	true	userinfo.token.claim
69fb5cbb-25d4-433c-8c8c-84b3210d32cf	true	id.token.claim
69fb5cbb-25d4-433c-8c8c-84b3210d32cf	true	access.token.claim
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	true	introspection.token.claim
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	true	userinfo.token.claim
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	website	user.attribute
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	true	id.token.claim
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	true	access.token.claim
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	website	claim.name
6dad0f60-3b9a-40b0-b0fc-15861937bcf3	String	jsonType.label
706031fc-e69b-4b30-a2cd-d86586704e5a	true	introspection.token.claim
706031fc-e69b-4b30-a2cd-d86586704e5a	true	userinfo.token.claim
706031fc-e69b-4b30-a2cd-d86586704e5a	gender	user.attribute
706031fc-e69b-4b30-a2cd-d86586704e5a	true	id.token.claim
706031fc-e69b-4b30-a2cd-d86586704e5a	true	access.token.claim
706031fc-e69b-4b30-a2cd-d86586704e5a	gender	claim.name
706031fc-e69b-4b30-a2cd-d86586704e5a	String	jsonType.label
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	true	introspection.token.claim
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	true	userinfo.token.claim
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	picture	user.attribute
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	true	id.token.claim
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	true	access.token.claim
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	picture	claim.name
7b76ef69-8dd0-45ab-88ab-aaf6369f87a8	String	jsonType.label
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	true	introspection.token.claim
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	true	userinfo.token.claim
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	zoneinfo	user.attribute
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	true	id.token.claim
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	true	access.token.claim
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	zoneinfo	claim.name
925f9ff3-8af1-4f5f-b99d-d4c0aaaa777b	String	jsonType.label
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	true	introspection.token.claim
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	true	userinfo.token.claim
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	username	user.attribute
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	true	id.token.claim
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	true	access.token.claim
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	preferred_username	claim.name
9dc6ef64-d6b5-4759-85ff-fd4d1e615eb1	String	jsonType.label
a2b1a04e-e131-4466-ab27-254d094f4710	true	introspection.token.claim
a2b1a04e-e131-4466-ab27-254d094f4710	true	userinfo.token.claim
a2b1a04e-e131-4466-ab27-254d094f4710	birthdate	user.attribute
a2b1a04e-e131-4466-ab27-254d094f4710	true	id.token.claim
a2b1a04e-e131-4466-ab27-254d094f4710	true	access.token.claim
a2b1a04e-e131-4466-ab27-254d094f4710	birthdate	claim.name
a2b1a04e-e131-4466-ab27-254d094f4710	String	jsonType.label
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	true	introspection.token.claim
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	true	userinfo.token.claim
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	firstName	user.attribute
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	true	id.token.claim
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	true	access.token.claim
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	given_name	claim.name
cb07a1e1-47cd-431e-9736-b4f2cc88c9bb	String	jsonType.label
e46437bb-abee-49f4-845e-3f03b40646e2	true	introspection.token.claim
e46437bb-abee-49f4-845e-3f03b40646e2	true	userinfo.token.claim
e46437bb-abee-49f4-845e-3f03b40646e2	profile	user.attribute
e46437bb-abee-49f4-845e-3f03b40646e2	true	id.token.claim
e46437bb-abee-49f4-845e-3f03b40646e2	true	access.token.claim
e46437bb-abee-49f4-845e-3f03b40646e2	profile	claim.name
e46437bb-abee-49f4-845e-3f03b40646e2	String	jsonType.label
ea9ab90b-f912-4b3a-954b-4cea951181f5	true	introspection.token.claim
ea9ab90b-f912-4b3a-954b-4cea951181f5	true	userinfo.token.claim
ea9ab90b-f912-4b3a-954b-4cea951181f5	middleName	user.attribute
ea9ab90b-f912-4b3a-954b-4cea951181f5	true	id.token.claim
ea9ab90b-f912-4b3a-954b-4cea951181f5	true	access.token.claim
ea9ab90b-f912-4b3a-954b-4cea951181f5	middle_name	claim.name
ea9ab90b-f912-4b3a-954b-4cea951181f5	String	jsonType.label
739bb76b-1aa0-42ea-aa65-b73359da0f3b	true	introspection.token.claim
739bb76b-1aa0-42ea-aa65-b73359da0f3b	true	userinfo.token.claim
739bb76b-1aa0-42ea-aa65-b73359da0f3b	emailVerified	user.attribute
739bb76b-1aa0-42ea-aa65-b73359da0f3b	true	id.token.claim
739bb76b-1aa0-42ea-aa65-b73359da0f3b	true	access.token.claim
739bb76b-1aa0-42ea-aa65-b73359da0f3b	email_verified	claim.name
739bb76b-1aa0-42ea-aa65-b73359da0f3b	boolean	jsonType.label
949d7607-46b2-4d18-af0c-240ca9a203d4	true	introspection.token.claim
949d7607-46b2-4d18-af0c-240ca9a203d4	true	userinfo.token.claim
949d7607-46b2-4d18-af0c-240ca9a203d4	email	user.attribute
949d7607-46b2-4d18-af0c-240ca9a203d4	true	id.token.claim
949d7607-46b2-4d18-af0c-240ca9a203d4	true	access.token.claim
949d7607-46b2-4d18-af0c-240ca9a203d4	email	claim.name
949d7607-46b2-4d18-af0c-240ca9a203d4	String	jsonType.label
67c4a03e-5824-4e21-bdae-25eed9d85d51	formatted	user.attribute.formatted
67c4a03e-5824-4e21-bdae-25eed9d85d51	country	user.attribute.country
67c4a03e-5824-4e21-bdae-25eed9d85d51	true	introspection.token.claim
67c4a03e-5824-4e21-bdae-25eed9d85d51	postal_code	user.attribute.postal_code
67c4a03e-5824-4e21-bdae-25eed9d85d51	true	userinfo.token.claim
67c4a03e-5824-4e21-bdae-25eed9d85d51	street	user.attribute.street
67c4a03e-5824-4e21-bdae-25eed9d85d51	true	id.token.claim
67c4a03e-5824-4e21-bdae-25eed9d85d51	region	user.attribute.region
67c4a03e-5824-4e21-bdae-25eed9d85d51	true	access.token.claim
67c4a03e-5824-4e21-bdae-25eed9d85d51	locality	user.attribute.locality
73ac7930-80cf-478a-aae1-18d5bc1305d3	true	introspection.token.claim
73ac7930-80cf-478a-aae1-18d5bc1305d3	true	userinfo.token.claim
73ac7930-80cf-478a-aae1-18d5bc1305d3	phoneNumberVerified	user.attribute
73ac7930-80cf-478a-aae1-18d5bc1305d3	true	id.token.claim
73ac7930-80cf-478a-aae1-18d5bc1305d3	true	access.token.claim
73ac7930-80cf-478a-aae1-18d5bc1305d3	phone_number_verified	claim.name
73ac7930-80cf-478a-aae1-18d5bc1305d3	boolean	jsonType.label
c4551015-57d0-4f0e-9363-dfbd742bc14d	true	introspection.token.claim
c4551015-57d0-4f0e-9363-dfbd742bc14d	true	userinfo.token.claim
c4551015-57d0-4f0e-9363-dfbd742bc14d	phoneNumber	user.attribute
c4551015-57d0-4f0e-9363-dfbd742bc14d	true	id.token.claim
c4551015-57d0-4f0e-9363-dfbd742bc14d	true	access.token.claim
c4551015-57d0-4f0e-9363-dfbd742bc14d	phone_number	claim.name
c4551015-57d0-4f0e-9363-dfbd742bc14d	String	jsonType.label
00c9d799-93aa-41fe-b6e7-44471d15d9fe	true	introspection.token.claim
00c9d799-93aa-41fe-b6e7-44471d15d9fe	true	multivalued
00c9d799-93aa-41fe-b6e7-44471d15d9fe	foo	user.attribute
00c9d799-93aa-41fe-b6e7-44471d15d9fe	true	access.token.claim
00c9d799-93aa-41fe-b6e7-44471d15d9fe	resource_access.${client_id}.roles	claim.name
00c9d799-93aa-41fe-b6e7-44471d15d9fe	String	jsonType.label
6655453e-abe3-4a7f-9117-43945e99b924	true	introspection.token.claim
6655453e-abe3-4a7f-9117-43945e99b924	true	access.token.claim
c0fed595-d503-4711-8f47-6ff0f70e6def	true	introspection.token.claim
c0fed595-d503-4711-8f47-6ff0f70e6def	true	multivalued
c0fed595-d503-4711-8f47-6ff0f70e6def	foo	user.attribute
c0fed595-d503-4711-8f47-6ff0f70e6def	true	access.token.claim
c0fed595-d503-4711-8f47-6ff0f70e6def	realm_access.roles	claim.name
c0fed595-d503-4711-8f47-6ff0f70e6def	String	jsonType.label
0486b434-31c6-4e6c-919d-7b9c41d1716a	true	introspection.token.claim
0486b434-31c6-4e6c-919d-7b9c41d1716a	true	access.token.claim
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	true	introspection.token.claim
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	true	userinfo.token.claim
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	username	user.attribute
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	true	id.token.claim
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	true	access.token.claim
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	upn	claim.name
0b8e95b1-31e6-41a9-a0cf-f1919f22eda2	String	jsonType.label
cfd63a00-31c5-47df-b15a-cddf49ab24c0	true	introspection.token.claim
cfd63a00-31c5-47df-b15a-cddf49ab24c0	true	multivalued
cfd63a00-31c5-47df-b15a-cddf49ab24c0	foo	user.attribute
cfd63a00-31c5-47df-b15a-cddf49ab24c0	true	id.token.claim
cfd63a00-31c5-47df-b15a-cddf49ab24c0	true	access.token.claim
cfd63a00-31c5-47df-b15a-cddf49ab24c0	groups	claim.name
cfd63a00-31c5-47df-b15a-cddf49ab24c0	String	jsonType.label
354202ba-75e8-4b0d-8a2e-ca5aa0eff427	true	introspection.token.claim
354202ba-75e8-4b0d-8a2e-ca5aa0eff427	true	id.token.claim
354202ba-75e8-4b0d-8a2e-ca5aa0eff427	true	access.token.claim
141994da-3ae6-4e92-bea0-2deecf060ced	AUTH_TIME	user.session.note
141994da-3ae6-4e92-bea0-2deecf060ced	true	introspection.token.claim
141994da-3ae6-4e92-bea0-2deecf060ced	true	id.token.claim
141994da-3ae6-4e92-bea0-2deecf060ced	true	access.token.claim
141994da-3ae6-4e92-bea0-2deecf060ced	auth_time	claim.name
141994da-3ae6-4e92-bea0-2deecf060ced	long	jsonType.label
7569f991-5b47-4eb4-9abf-bc309f9b4f38	true	introspection.token.claim
7569f991-5b47-4eb4-9abf-bc309f9b4f38	true	access.token.claim
472eda5c-ecb6-48f2-adde-6e750b3b47cb	clientAddress	user.session.note
472eda5c-ecb6-48f2-adde-6e750b3b47cb	true	introspection.token.claim
472eda5c-ecb6-48f2-adde-6e750b3b47cb	true	id.token.claim
472eda5c-ecb6-48f2-adde-6e750b3b47cb	true	access.token.claim
472eda5c-ecb6-48f2-adde-6e750b3b47cb	clientAddress	claim.name
472eda5c-ecb6-48f2-adde-6e750b3b47cb	String	jsonType.label
556d64a7-2931-44e0-8d34-42b998703b2b	clientHost	user.session.note
556d64a7-2931-44e0-8d34-42b998703b2b	true	introspection.token.claim
556d64a7-2931-44e0-8d34-42b998703b2b	true	id.token.claim
556d64a7-2931-44e0-8d34-42b998703b2b	true	access.token.claim
556d64a7-2931-44e0-8d34-42b998703b2b	clientHost	claim.name
556d64a7-2931-44e0-8d34-42b998703b2b	String	jsonType.label
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	client_id	user.session.note
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	true	introspection.token.claim
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	true	id.token.claim
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	true	access.token.claim
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	client_id	claim.name
d7bd9ed6-903c-4efd-a34b-154fbc054c8d	String	jsonType.label
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	true	introspection.token.claim
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	true	multivalued
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	true	id.token.claim
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	true	access.token.claim
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	organization	claim.name
a0fc68b8-2fd1-4a81-b67b-d929c574a1a0	String	jsonType.label
dee179e9-3252-4fb5-9794-183b5dad20d0	false	single
dee179e9-3252-4fb5-9794-183b5dad20d0	Basic	attribute.nameformat
dee179e9-3252-4fb5-9794-183b5dad20d0	Role	attribute.name
0848c473-baa9-4805-9c57-8d212305fd56	true	introspection.token.claim
0848c473-baa9-4805-9c57-8d212305fd56	true	userinfo.token.claim
0848c473-baa9-4805-9c57-8d212305fd56	profile	user.attribute
0848c473-baa9-4805-9c57-8d212305fd56	true	id.token.claim
0848c473-baa9-4805-9c57-8d212305fd56	true	access.token.claim
0848c473-baa9-4805-9c57-8d212305fd56	profile	claim.name
0848c473-baa9-4805-9c57-8d212305fd56	String	jsonType.label
45e8e325-1ab7-4599-83c3-16a6b54abb78	true	introspection.token.claim
45e8e325-1ab7-4599-83c3-16a6b54abb78	true	userinfo.token.claim
45e8e325-1ab7-4599-83c3-16a6b54abb78	website	user.attribute
45e8e325-1ab7-4599-83c3-16a6b54abb78	true	id.token.claim
45e8e325-1ab7-4599-83c3-16a6b54abb78	true	access.token.claim
45e8e325-1ab7-4599-83c3-16a6b54abb78	website	claim.name
45e8e325-1ab7-4599-83c3-16a6b54abb78	String	jsonType.label
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	true	introspection.token.claim
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	true	userinfo.token.claim
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	picture	user.attribute
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	true	id.token.claim
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	true	access.token.claim
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	picture	claim.name
5cdb1c09-d20f-42c5-a82d-7d39349a3a5d	String	jsonType.label
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	true	introspection.token.claim
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	true	userinfo.token.claim
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	username	user.attribute
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	true	id.token.claim
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	true	access.token.claim
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	preferred_username	claim.name
61b98f2c-d6a9-451e-b775-2ceb4d7beb66	String	jsonType.label
6bfe8fef-9b62-41be-b068-f58851dfad46	true	introspection.token.claim
6bfe8fef-9b62-41be-b068-f58851dfad46	true	userinfo.token.claim
6bfe8fef-9b62-41be-b068-f58851dfad46	firstName	user.attribute
6bfe8fef-9b62-41be-b068-f58851dfad46	true	id.token.claim
6bfe8fef-9b62-41be-b068-f58851dfad46	true	access.token.claim
6bfe8fef-9b62-41be-b068-f58851dfad46	given_name	claim.name
6bfe8fef-9b62-41be-b068-f58851dfad46	String	jsonType.label
75d66380-431e-42dc-8d00-3aa291e23afa	true	introspection.token.claim
75d66380-431e-42dc-8d00-3aa291e23afa	true	userinfo.token.claim
75d66380-431e-42dc-8d00-3aa291e23afa	middleName	user.attribute
75d66380-431e-42dc-8d00-3aa291e23afa	true	id.token.claim
75d66380-431e-42dc-8d00-3aa291e23afa	true	access.token.claim
75d66380-431e-42dc-8d00-3aa291e23afa	middle_name	claim.name
75d66380-431e-42dc-8d00-3aa291e23afa	String	jsonType.label
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	true	introspection.token.claim
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	true	userinfo.token.claim
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	updatedAt	user.attribute
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	true	id.token.claim
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	true	access.token.claim
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	updated_at	claim.name
85ecea62-fb03-4c58-99ee-fe70ab9bfa03	long	jsonType.label
8615514f-69fa-44ce-930d-356b7a632362	true	introspection.token.claim
8615514f-69fa-44ce-930d-356b7a632362	true	userinfo.token.claim
8615514f-69fa-44ce-930d-356b7a632362	zoneinfo	user.attribute
8615514f-69fa-44ce-930d-356b7a632362	true	id.token.claim
8615514f-69fa-44ce-930d-356b7a632362	true	access.token.claim
8615514f-69fa-44ce-930d-356b7a632362	zoneinfo	claim.name
8615514f-69fa-44ce-930d-356b7a632362	String	jsonType.label
9a77bab1-b85c-4959-9379-9caaf48282a6	true	introspection.token.claim
9a77bab1-b85c-4959-9379-9caaf48282a6	true	userinfo.token.claim
9a77bab1-b85c-4959-9379-9caaf48282a6	gender	user.attribute
9a77bab1-b85c-4959-9379-9caaf48282a6	true	id.token.claim
9a77bab1-b85c-4959-9379-9caaf48282a6	true	access.token.claim
9a77bab1-b85c-4959-9379-9caaf48282a6	gender	claim.name
9a77bab1-b85c-4959-9379-9caaf48282a6	String	jsonType.label
baf54263-3389-4b97-a367-f6b2797e4634	true	introspection.token.claim
baf54263-3389-4b97-a367-f6b2797e4634	true	userinfo.token.claim
baf54263-3389-4b97-a367-f6b2797e4634	locale	user.attribute
baf54263-3389-4b97-a367-f6b2797e4634	true	id.token.claim
baf54263-3389-4b97-a367-f6b2797e4634	true	access.token.claim
baf54263-3389-4b97-a367-f6b2797e4634	locale	claim.name
baf54263-3389-4b97-a367-f6b2797e4634	String	jsonType.label
c0333e50-9a17-4c6c-be7d-535f327d3ed7	true	introspection.token.claim
c0333e50-9a17-4c6c-be7d-535f327d3ed7	true	userinfo.token.claim
c0333e50-9a17-4c6c-be7d-535f327d3ed7	true	id.token.claim
c0333e50-9a17-4c6c-be7d-535f327d3ed7	true	access.token.claim
eb6d7510-51c6-4335-a558-b74f9e2d67cd	true	introspection.token.claim
eb6d7510-51c6-4335-a558-b74f9e2d67cd	true	userinfo.token.claim
eb6d7510-51c6-4335-a558-b74f9e2d67cd	lastName	user.attribute
eb6d7510-51c6-4335-a558-b74f9e2d67cd	true	id.token.claim
eb6d7510-51c6-4335-a558-b74f9e2d67cd	true	access.token.claim
eb6d7510-51c6-4335-a558-b74f9e2d67cd	family_name	claim.name
eb6d7510-51c6-4335-a558-b74f9e2d67cd	String	jsonType.label
ed45f755-b43b-4a99-8d9d-9699e6515dbe	true	introspection.token.claim
ed45f755-b43b-4a99-8d9d-9699e6515dbe	true	userinfo.token.claim
ed45f755-b43b-4a99-8d9d-9699e6515dbe	nickname	user.attribute
ed45f755-b43b-4a99-8d9d-9699e6515dbe	true	id.token.claim
ed45f755-b43b-4a99-8d9d-9699e6515dbe	true	access.token.claim
ed45f755-b43b-4a99-8d9d-9699e6515dbe	nickname	claim.name
ed45f755-b43b-4a99-8d9d-9699e6515dbe	String	jsonType.label
fd209626-8cbe-4ef1-aed0-b4084ba953ba	true	introspection.token.claim
fd209626-8cbe-4ef1-aed0-b4084ba953ba	true	userinfo.token.claim
fd209626-8cbe-4ef1-aed0-b4084ba953ba	birthdate	user.attribute
fd209626-8cbe-4ef1-aed0-b4084ba953ba	true	id.token.claim
fd209626-8cbe-4ef1-aed0-b4084ba953ba	true	access.token.claim
fd209626-8cbe-4ef1-aed0-b4084ba953ba	birthdate	claim.name
fd209626-8cbe-4ef1-aed0-b4084ba953ba	String	jsonType.label
997047e9-278f-4ea3-9d21-d0b725280084	true	introspection.token.claim
997047e9-278f-4ea3-9d21-d0b725280084	true	userinfo.token.claim
997047e9-278f-4ea3-9d21-d0b725280084	emailVerified	user.attribute
997047e9-278f-4ea3-9d21-d0b725280084	true	id.token.claim
997047e9-278f-4ea3-9d21-d0b725280084	true	access.token.claim
997047e9-278f-4ea3-9d21-d0b725280084	email_verified	claim.name
997047e9-278f-4ea3-9d21-d0b725280084	boolean	jsonType.label
e10a0c93-fd67-4ca6-a437-740be926a4c2	true	introspection.token.claim
e10a0c93-fd67-4ca6-a437-740be926a4c2	true	userinfo.token.claim
e10a0c93-fd67-4ca6-a437-740be926a4c2	email	user.attribute
e10a0c93-fd67-4ca6-a437-740be926a4c2	true	id.token.claim
e10a0c93-fd67-4ca6-a437-740be926a4c2	true	access.token.claim
e10a0c93-fd67-4ca6-a437-740be926a4c2	email	claim.name
e10a0c93-fd67-4ca6-a437-740be926a4c2	String	jsonType.label
84e3e0d6-8829-424e-bd53-ff357f4c32f7	formatted	user.attribute.formatted
84e3e0d6-8829-424e-bd53-ff357f4c32f7	country	user.attribute.country
84e3e0d6-8829-424e-bd53-ff357f4c32f7	true	introspection.token.claim
84e3e0d6-8829-424e-bd53-ff357f4c32f7	postal_code	user.attribute.postal_code
84e3e0d6-8829-424e-bd53-ff357f4c32f7	true	userinfo.token.claim
84e3e0d6-8829-424e-bd53-ff357f4c32f7	street	user.attribute.street
84e3e0d6-8829-424e-bd53-ff357f4c32f7	true	id.token.claim
84e3e0d6-8829-424e-bd53-ff357f4c32f7	region	user.attribute.region
84e3e0d6-8829-424e-bd53-ff357f4c32f7	true	access.token.claim
84e3e0d6-8829-424e-bd53-ff357f4c32f7	locality	user.attribute.locality
534df5f1-bd3b-4f09-87b0-b55da2b4be78	true	introspection.token.claim
534df5f1-bd3b-4f09-87b0-b55da2b4be78	true	userinfo.token.claim
534df5f1-bd3b-4f09-87b0-b55da2b4be78	phoneNumber	user.attribute
534df5f1-bd3b-4f09-87b0-b55da2b4be78	true	id.token.claim
534df5f1-bd3b-4f09-87b0-b55da2b4be78	true	access.token.claim
534df5f1-bd3b-4f09-87b0-b55da2b4be78	phone_number	claim.name
534df5f1-bd3b-4f09-87b0-b55da2b4be78	String	jsonType.label
838e3888-0136-4edf-9e6a-f892ea37e40d	true	introspection.token.claim
838e3888-0136-4edf-9e6a-f892ea37e40d	true	userinfo.token.claim
838e3888-0136-4edf-9e6a-f892ea37e40d	phoneNumberVerified	user.attribute
838e3888-0136-4edf-9e6a-f892ea37e40d	true	id.token.claim
838e3888-0136-4edf-9e6a-f892ea37e40d	true	access.token.claim
838e3888-0136-4edf-9e6a-f892ea37e40d	phone_number_verified	claim.name
838e3888-0136-4edf-9e6a-f892ea37e40d	boolean	jsonType.label
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	true	introspection.token.claim
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	true	multivalued
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	foo	user.attribute
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	true	access.token.claim
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	realm_access.roles	claim.name
0ba7ed0e-4a5a-4843-a6e9-59ca00684f5b	String	jsonType.label
686a1a10-24be-4440-9360-363994400628	true	introspection.token.claim
686a1a10-24be-4440-9360-363994400628	true	access.token.claim
899f2c62-8e34-43d3-a692-50c3d28c6977	true	introspection.token.claim
899f2c62-8e34-43d3-a692-50c3d28c6977	true	multivalued
899f2c62-8e34-43d3-a692-50c3d28c6977	foo	user.attribute
899f2c62-8e34-43d3-a692-50c3d28c6977	true	access.token.claim
899f2c62-8e34-43d3-a692-50c3d28c6977	resource_access.${client_id}.roles	claim.name
899f2c62-8e34-43d3-a692-50c3d28c6977	String	jsonType.label
7b4377be-1918-4443-9497-11c51c2ce608	true	introspection.token.claim
7b4377be-1918-4443-9497-11c51c2ce608	true	access.token.claim
730bdbfe-a842-40ad-8a0f-35ce66c597f1	true	introspection.token.claim
730bdbfe-a842-40ad-8a0f-35ce66c597f1	true	userinfo.token.claim
730bdbfe-a842-40ad-8a0f-35ce66c597f1	username	user.attribute
730bdbfe-a842-40ad-8a0f-35ce66c597f1	true	id.token.claim
730bdbfe-a842-40ad-8a0f-35ce66c597f1	true	access.token.claim
730bdbfe-a842-40ad-8a0f-35ce66c597f1	upn	claim.name
730bdbfe-a842-40ad-8a0f-35ce66c597f1	String	jsonType.label
eb677d4a-2711-49e4-88c7-af045dca05a3	true	introspection.token.claim
eb677d4a-2711-49e4-88c7-af045dca05a3	true	multivalued
eb677d4a-2711-49e4-88c7-af045dca05a3	foo	user.attribute
eb677d4a-2711-49e4-88c7-af045dca05a3	true	id.token.claim
eb677d4a-2711-49e4-88c7-af045dca05a3	true	access.token.claim
eb677d4a-2711-49e4-88c7-af045dca05a3	groups	claim.name
eb677d4a-2711-49e4-88c7-af045dca05a3	String	jsonType.label
2a7ed9e2-5107-48c5-9ffa-ee3ad04ee609	true	introspection.token.claim
2a7ed9e2-5107-48c5-9ffa-ee3ad04ee609	true	id.token.claim
2a7ed9e2-5107-48c5-9ffa-ee3ad04ee609	true	access.token.claim
007dd1f1-6a55-4917-b7b3-0900d2979346	true	introspection.token.claim
007dd1f1-6a55-4917-b7b3-0900d2979346	true	access.token.claim
99b7c2a3-0075-4c07-aef6-de377a977609	AUTH_TIME	user.session.note
99b7c2a3-0075-4c07-aef6-de377a977609	true	introspection.token.claim
99b7c2a3-0075-4c07-aef6-de377a977609	true	id.token.claim
99b7c2a3-0075-4c07-aef6-de377a977609	true	access.token.claim
99b7c2a3-0075-4c07-aef6-de377a977609	auth_time	claim.name
99b7c2a3-0075-4c07-aef6-de377a977609	long	jsonType.label
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	clientAddress	user.session.note
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	true	introspection.token.claim
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	true	id.token.claim
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	true	access.token.claim
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	clientAddress	claim.name
353ad8c7-2e7a-4f44-8c0c-b44674c5ef29	String	jsonType.label
5afddf16-9606-4c94-ac67-3b1f89db5774	client_id	user.session.note
5afddf16-9606-4c94-ac67-3b1f89db5774	true	introspection.token.claim
5afddf16-9606-4c94-ac67-3b1f89db5774	true	id.token.claim
5afddf16-9606-4c94-ac67-3b1f89db5774	true	access.token.claim
5afddf16-9606-4c94-ac67-3b1f89db5774	client_id	claim.name
5afddf16-9606-4c94-ac67-3b1f89db5774	String	jsonType.label
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	clientHost	user.session.note
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	true	introspection.token.claim
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	true	id.token.claim
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	true	access.token.claim
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	clientHost	claim.name
9b3c4ef0-6b9f-4b10-ba2c-1fa00b46de8a	String	jsonType.label
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	true	introspection.token.claim
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	true	multivalued
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	true	id.token.claim
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	true	access.token.claim
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	organization	claim.name
3b76b5c2-4f3a-4613-b6e0-58e9d52d83f6	String	jsonType.label
5ac106a3-6077-486f-a3ec-f1b5188cee8d	true	introspection.token.claim
5ac106a3-6077-486f-a3ec-f1b5188cee8d	true	userinfo.token.claim
5ac106a3-6077-486f-a3ec-f1b5188cee8d	locale	user.attribute
5ac106a3-6077-486f-a3ec-f1b5188cee8d	true	id.token.claim
5ac106a3-6077-486f-a3ec-f1b5188cee8d	true	access.token.claim
5ac106a3-6077-486f-a3ec-f1b5188cee8d	locale	claim.name
5ac106a3-6077-486f-a3ec-f1b5188cee8d	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
74fb1520-0e85-4576-bac4-f72784d7f550	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	41fc49ae-52ee-4330-a1a1-8af3802bc5af	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	f0ff90ae-90c0-4fed-b02e-91c4c5385ce1	573bef42-1473-4cd5-8e68-f45c9b9d64b1	b1a1a1e0-2acd-475d-9623-6044b79d1659	f596a2e9-bcb8-472d-959c-ee2cc3076e11	95c24d13-78ca-4699-a279-e28110af37d5	2592000	f	900	t	f	d517c279-1a13-45eb-903c-20d31f6e60d9	0	f	0	0	4320f1ff-ff3b-4258-a106-af4e8c5c5357
c09c14f3-a5f7-4baa-be03-70f28dad6f95	60	300	300	\N	\N	\N	t	f	0	\N	myrealm	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	2477d9ab-3104-4cca-baaf-650e8260bccd	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	3b8cbb0a-72bb-4f9c-a524-eae69c8a57b9	96906da9-abfc-445c-8e78-476e6c133599	11db3f92-8c30-49e2-8ac1-8adb23bf5027	cd630879-8659-4c60-a8be-95c35c372224	f206b76a-9b18-4776-ace9-7a8803097fbe	2592000	f	900	t	f	7d40a63e-ff04-4e8a-b16b-8d9feafd0694	0	f	0	0	e220c7fb-27f9-448e-842b-50ab8a4959ec
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	74fb1520-0e85-4576-bac4-f72784d7f550	
_browser_header.xContentTypeOptions	74fb1520-0e85-4576-bac4-f72784d7f550	nosniff
_browser_header.referrerPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	no-referrer
_browser_header.xRobotsTag	74fb1520-0e85-4576-bac4-f72784d7f550	none
_browser_header.xFrameOptions	74fb1520-0e85-4576-bac4-f72784d7f550	SAMEORIGIN
_browser_header.contentSecurityPolicy	74fb1520-0e85-4576-bac4-f72784d7f550	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.strictTransportSecurity	74fb1520-0e85-4576-bac4-f72784d7f550	max-age=31536000; includeSubDomains
bruteForceProtected	74fb1520-0e85-4576-bac4-f72784d7f550	false
permanentLockout	74fb1520-0e85-4576-bac4-f72784d7f550	false
maxTemporaryLockouts	74fb1520-0e85-4576-bac4-f72784d7f550	0
bruteForceStrategy	74fb1520-0e85-4576-bac4-f72784d7f550	MULTIPLE
maxFailureWaitSeconds	74fb1520-0e85-4576-bac4-f72784d7f550	900
minimumQuickLoginWaitSeconds	74fb1520-0e85-4576-bac4-f72784d7f550	60
waitIncrementSeconds	74fb1520-0e85-4576-bac4-f72784d7f550	60
quickLoginCheckMilliSeconds	74fb1520-0e85-4576-bac4-f72784d7f550	1000
maxDeltaTimeSeconds	74fb1520-0e85-4576-bac4-f72784d7f550	43200
failureFactor	74fb1520-0e85-4576-bac4-f72784d7f550	30
realmReusableOtpCode	74fb1520-0e85-4576-bac4-f72784d7f550	false
firstBrokerLoginFlowId	74fb1520-0e85-4576-bac4-f72784d7f550	9bba66b1-bc34-4dcf-ab42-e4eba70a85c7
displayName	74fb1520-0e85-4576-bac4-f72784d7f550	Keycloak
displayNameHtml	74fb1520-0e85-4576-bac4-f72784d7f550	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	74fb1520-0e85-4576-bac4-f72784d7f550	RS256
offlineSessionMaxLifespanEnabled	74fb1520-0e85-4576-bac4-f72784d7f550	false
offlineSessionMaxLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	5184000
_browser_header.contentSecurityPolicyReportOnly	c09c14f3-a5f7-4baa-be03-70f28dad6f95	
_browser_header.xContentTypeOptions	c09c14f3-a5f7-4baa-be03-70f28dad6f95	nosniff
_browser_header.referrerPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	no-referrer
_browser_header.xRobotsTag	c09c14f3-a5f7-4baa-be03-70f28dad6f95	none
_browser_header.xFrameOptions	c09c14f3-a5f7-4baa-be03-70f28dad6f95	SAMEORIGIN
_browser_header.contentSecurityPolicy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.strictTransportSecurity	c09c14f3-a5f7-4baa-be03-70f28dad6f95	max-age=31536000; includeSubDomains
bruteForceProtected	c09c14f3-a5f7-4baa-be03-70f28dad6f95	false
permanentLockout	c09c14f3-a5f7-4baa-be03-70f28dad6f95	false
maxTemporaryLockouts	c09c14f3-a5f7-4baa-be03-70f28dad6f95	0
bruteForceStrategy	c09c14f3-a5f7-4baa-be03-70f28dad6f95	MULTIPLE
maxFailureWaitSeconds	c09c14f3-a5f7-4baa-be03-70f28dad6f95	900
minimumQuickLoginWaitSeconds	c09c14f3-a5f7-4baa-be03-70f28dad6f95	60
waitIncrementSeconds	c09c14f3-a5f7-4baa-be03-70f28dad6f95	60
quickLoginCheckMilliSeconds	c09c14f3-a5f7-4baa-be03-70f28dad6f95	1000
maxDeltaTimeSeconds	c09c14f3-a5f7-4baa-be03-70f28dad6f95	43200
failureFactor	c09c14f3-a5f7-4baa-be03-70f28dad6f95	30
realmReusableOtpCode	c09c14f3-a5f7-4baa-be03-70f28dad6f95	false
defaultSignatureAlgorithm	c09c14f3-a5f7-4baa-be03-70f28dad6f95	RS256
offlineSessionMaxLifespanEnabled	c09c14f3-a5f7-4baa-be03-70f28dad6f95	false
offlineSessionMaxLifespan	c09c14f3-a5f7-4baa-be03-70f28dad6f95	5184000
actionTokenGeneratedByAdminLifespan	c09c14f3-a5f7-4baa-be03-70f28dad6f95	43200
actionTokenGeneratedByUserLifespan	c09c14f3-a5f7-4baa-be03-70f28dad6f95	300
oauth2DeviceCodeLifespan	c09c14f3-a5f7-4baa-be03-70f28dad6f95	600
oauth2DevicePollingInterval	c09c14f3-a5f7-4baa-be03-70f28dad6f95	5
webAuthnPolicyRpEntityName	c09c14f3-a5f7-4baa-be03-70f28dad6f95	keycloak
webAuthnPolicySignatureAlgorithms	c09c14f3-a5f7-4baa-be03-70f28dad6f95	ES256,RS256
webAuthnPolicyRpId	c09c14f3-a5f7-4baa-be03-70f28dad6f95	
webAuthnPolicyAttestationConveyancePreference	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyAuthenticatorAttachment	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyRequireResidentKey	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyUserVerificationRequirement	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyCreateTimeout	c09c14f3-a5f7-4baa-be03-70f28dad6f95	0
webAuthnPolicyAvoidSameAuthenticatorRegister	c09c14f3-a5f7-4baa-be03-70f28dad6f95	false
webAuthnPolicyRpEntityNamePasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	ES256,RS256
webAuthnPolicyRpIdPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	
webAuthnPolicyAttestationConveyancePreferencePasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyRequireResidentKeyPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	not specified
webAuthnPolicyCreateTimeoutPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	false
cibaBackchannelTokenDeliveryMode	c09c14f3-a5f7-4baa-be03-70f28dad6f95	poll
cibaExpiresIn	c09c14f3-a5f7-4baa-be03-70f28dad6f95	120
cibaInterval	c09c14f3-a5f7-4baa-be03-70f28dad6f95	5
cibaAuthRequestedUserHint	c09c14f3-a5f7-4baa-be03-70f28dad6f95	login_hint
parRequestUriLifespan	c09c14f3-a5f7-4baa-be03-70f28dad6f95	60
firstBrokerLoginFlowId	c09c14f3-a5f7-4baa-be03-70f28dad6f95	6eb93342-0143-4a21-b1c2-c2e4598585c5
frontendUrl	74fb1520-0e85-4576-bac4-f72784d7f550	https://auth.renzoproject.site/
saml.signature.algorithm	74fb1520-0e85-4576-bac4-f72784d7f550	
acr.loa.map	74fb1520-0e85-4576-bac4-f72784d7f550	{}
cibaBackchannelTokenDeliveryMode	74fb1520-0e85-4576-bac4-f72784d7f550	poll
cibaExpiresIn	74fb1520-0e85-4576-bac4-f72784d7f550	120
cibaAuthRequestedUserHint	74fb1520-0e85-4576-bac4-f72784d7f550	login_hint
parRequestUriLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	60
cibaInterval	74fb1520-0e85-4576-bac4-f72784d7f550	5
organizationsEnabled	74fb1520-0e85-4576-bac4-f72784d7f550	false
adminPermissionsEnabled	74fb1520-0e85-4576-bac4-f72784d7f550	false
verifiableCredentialsEnabled	74fb1520-0e85-4576-bac4-f72784d7f550	false
actionTokenGeneratedByAdminLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	43200
actionTokenGeneratedByUserLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	300
oauth2DeviceCodeLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	600
oauth2DevicePollingInterval	74fb1520-0e85-4576-bac4-f72784d7f550	5
clientSessionIdleTimeout	74fb1520-0e85-4576-bac4-f72784d7f550	0
clientSessionMaxLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	0
clientOfflineSessionIdleTimeout	74fb1520-0e85-4576-bac4-f72784d7f550	0
clientOfflineSessionMaxLifespan	74fb1520-0e85-4576-bac4-f72784d7f550	0
webAuthnPolicyRpEntityName	74fb1520-0e85-4576-bac4-f72784d7f550	keycloak
webAuthnPolicySignatureAlgorithms	74fb1520-0e85-4576-bac4-f72784d7f550	ES256,RS256
webAuthnPolicyRpId	74fb1520-0e85-4576-bac4-f72784d7f550	
webAuthnPolicyAttestationConveyancePreference	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyAuthenticatorAttachment	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyRequireResidentKey	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyUserVerificationRequirement	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyCreateTimeout	74fb1520-0e85-4576-bac4-f72784d7f550	0
webAuthnPolicyAvoidSameAuthenticatorRegister	74fb1520-0e85-4576-bac4-f72784d7f550	false
webAuthnPolicyRpEntityNamePasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	ES256,RS256
webAuthnPolicyRpIdPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	
webAuthnPolicyAttestationConveyancePreferencePasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyRequireResidentKeyPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	not specified
webAuthnPolicyCreateTimeoutPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	74fb1520-0e85-4576-bac4-f72784d7f550	false
client-policies.profiles	74fb1520-0e85-4576-bac4-f72784d7f550	{"profiles":[]}
client-policies.policies	74fb1520-0e85-4576-bac4-f72784d7f550	{"policies":[]}
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_events_listeners (realm_id, value) FROM stdin;
c09c14f3-a5f7-4baa-be03-70f28dad6f95	jboss-logging
74fb1520-0e85-4576-bac4-f72784d7f550	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	74fb1520-0e85-4576-bac4-f72784d7f550
password	password	t	t	c09c14f3-a5f7-4baa-be03-70f28dad6f95
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realm_supported_locales (realm_id, value) FROM stdin;
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.redirect_uris (client_id, value) FROM stdin;
1ba364ae-bf5f-4e45-8ef7-59edee20d88c	/realms/master/account/*
45d84e44-7b18-42db-acb0-3deaf693558b	/realms/master/account/*
dd90a450-d5ef-4d13-b211-5a794446a37f	/admin/master/console/*
afaad5c0-a7e7-4ddc-a651-bf33b68e7ddc	/realms/myrealm/account/*
c4ac8131-45b7-428a-b2bc-0966287ddf96	/realms/myrealm/account/*
82806441-9195-44ea-a4e7-cbeb15c17556	/admin/myrealm/console/*
f6148499-fe48-40cf-ac62-7d061c084312	/*
e3e3a6ba-b828-4ebd-9415-88988901eef1	/*
3854aed3-37b1-42c9-9bb0-4a40484449be	/*
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
e879595b-5515-4561-bf17-91c3d383fb49	VERIFY_EMAIL	Verify Email	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	VERIFY_EMAIL	50
c3379d6a-2524-4df7-bf67-80b16605c242	UPDATE_PROFILE	Update Profile	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	UPDATE_PROFILE	40
410df8d2-1ca8-4190-832e-254e43fd6585	CONFIGURE_TOTP	Configure OTP	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	CONFIGURE_TOTP	10
e2a3fbcd-de13-474e-92fd-72bdf008ed98	UPDATE_PASSWORD	Update Password	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	UPDATE_PASSWORD	30
dc9a3a2c-ea54-4c22-adef-0d57147c6554	TERMS_AND_CONDITIONS	Terms and Conditions	74fb1520-0e85-4576-bac4-f72784d7f550	f	f	TERMS_AND_CONDITIONS	20
f7d47fb9-c511-4556-8859-98ce64e0f3fe	delete_account	Delete Account	74fb1520-0e85-4576-bac4-f72784d7f550	f	f	delete_account	60
5b5432e9-764c-4278-bafb-f4aaadd65e2c	delete_credential	Delete Credential	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	delete_credential	100
24e89f9a-a7c0-4351-a7ea-bf5aeb694349	update_user_locale	Update User Locale	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	update_user_locale	1000
c351dbba-e7f2-40e0-b166-c1c232762680	CONFIGURE_RECOVERY_AUTHN_CODES	Recovery Authentication Codes	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	CONFIGURE_RECOVERY_AUTHN_CODES	120
f2d76742-f654-4072-adef-79d7b985861e	webauthn-register	Webauthn Register	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	webauthn-register	70
b822c2ca-3595-4c91-a082-e025579c8d28	webauthn-register-passwordless	Webauthn Register Passwordless	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	webauthn-register-passwordless	80
8c06e50b-37ef-415d-9b91-2165a13b93e4	VERIFY_PROFILE	Verify Profile	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	VERIFY_PROFILE	90
8e2c7d6f-51e6-4ecb-b298-d58edbbb068e	idp_link	Linking Identity Provider	74fb1520-0e85-4576-bac4-f72784d7f550	t	f	idp_link	110
beac83db-f054-4c2c-a325-53849835c4d3	VERIFY_EMAIL	Verify Email	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	VERIFY_EMAIL	50
0012ff65-e251-48c4-81f5-c7386721f6bd	UPDATE_PROFILE	Update Profile	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	UPDATE_PROFILE	40
cba9550a-6d7c-4384-84af-3b948c1d23ed	CONFIGURE_TOTP	Configure OTP	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	CONFIGURE_TOTP	10
3c591c70-0d86-4dcd-8b2a-d56820282edc	UPDATE_PASSWORD	Update Password	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	UPDATE_PASSWORD	30
0cfe5e51-2e01-4019-b27d-d39cc20ce0c2	TERMS_AND_CONDITIONS	Terms and Conditions	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f	f	TERMS_AND_CONDITIONS	20
2894d551-d32d-4416-a333-a8014c5f135a	delete_account	Delete Account	c09c14f3-a5f7-4baa-be03-70f28dad6f95	f	f	delete_account	60
f19dde82-1094-4fb1-a3ce-26de151be2f3	delete_credential	Delete Credential	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	delete_credential	100
fe56bdc8-e72d-4102-8c94-afe90f556ad6	update_user_locale	Update User Locale	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	update_user_locale	1000
9357f808-aa62-46c1-bb07-09cdd5399a43	CONFIGURE_RECOVERY_AUTHN_CODES	Recovery Authentication Codes	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	CONFIGURE_RECOVERY_AUTHN_CODES	120
8ed20b12-6ba4-4902-832d-ddd8ea7c5aee	webauthn-register	Webauthn Register	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	webauthn-register	70
2073ff9c-4e1a-4dc0-949f-781242d67820	webauthn-register-passwordless	Webauthn Register Passwordless	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	webauthn-register-passwordless	80
2dc05ce3-37c0-4b5c-96eb-f4659bda5c33	VERIFY_PROFILE	Verify Profile	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	VERIFY_PROFILE	90
d0d2550f-dbd5-4cd6-a118-721a695dba76	idp_link	Linking Identity Provider	c09c14f3-a5f7-4baa-be03-70f28dad6f95	t	f	idp_link	110
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: revoked_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.revoked_token (id, expire) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scope_mapping (client_id, role_id) FROM stdin;
45d84e44-7b18-42db-acb0-3deaf693558b	a9122d09-2de4-4652-9af3-30145f2b2b62
45d84e44-7b18-42db-acb0-3deaf693558b	54765c2f-0f6e-4a28-9093-b68a4d6f81ad
c4ac8131-45b7-428a-b2bc-0966287ddf96	3899ca05-1d4f-450d-9b90-136174a882a4
c4ac8131-45b7-428a-b2bc-0966287ddf96	58b3cee6-b755-417c-9846-1c5d304916ba
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: server_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.server_config (server_config_key, value, version) FROM stdin;
JGROUPS_ADDRESS_SEQUENCE	11	11
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_attribute (name, value, user_id, id, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
is_temporary_admin	true	f6c10a19-f370-4d01-a6ae-a9e1477cb9fa	b245e480-a00d-4ccd-a0ed-43996272a38a	\N	\N	\N
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
f6c10a19-f370-4d01-a6ae-a9e1477cb9fa	\N	0d8ff2bb-be40-4b3a-8f64-80e2a8255408	f	t	\N	\N	\N	74fb1520-0e85-4576-bac4-f72784d7f550	admin	1757567872005	\N	0
8f48cf51-e135-42d0-bd23-ce79a24f2d43	\N	99fed091-68ee-4884-8568-448b5cbe6e98	f	t	\N	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	service-account-gateway-service	1757577690439	f6148499-fe48-40cf-ac62-7d061c084312	0
cfd033b1-890f-4a2e-932f-b77f3400ec96	\N	4cd29d8d-d92d-4d94-9d54-c615305fe56f	f	t	\N	\N	\N	c09c14f3-a5f7-4baa-be03-70f28dad6f95	service-account-auth-service	1757578109103	e3e3a6ba-b828-4ebd-9415-88988901eef1	0
82a427d9-66af-477a-a670-f49fd33d50a4	sample@gmail.com	sample@gmail.com	f	t	\N	admin	admin	c09c14f3-a5f7-4baa-be03-70f28dad6f95	admin	1757838934460	\N	0
ae32774b-1bed-4ab5-afa0-1ee0295fb9eb	john.doe@example.com	john.doe@example.com	t	t	\N	John	Doe	c09c14f3-a5f7-4baa-be03-70f28dad6f95	john_doe	1757851747887	\N	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_group_membership (group_id, user_id, membership_type) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role_mapping (role_id, user_id) FROM stdin;
4320f1ff-ff3b-4258-a106-af4e8c5c5357	f6c10a19-f370-4d01-a6ae-a9e1477cb9fa
656c3404-26ad-441b-a9eb-34a37249209c	f6c10a19-f370-4d01-a6ae-a9e1477cb9fa
e220c7fb-27f9-448e-842b-50ab8a4959ec	8f48cf51-e135-42d0-bd23-ce79a24f2d43
e220c7fb-27f9-448e-842b-50ab8a4959ec	cfd033b1-890f-4a2e-932f-b77f3400ec96
e220c7fb-27f9-448e-842b-50ab8a4959ec	82a427d9-66af-477a-a670-f49fd33d50a4
fbe2ea14-3e6c-4e30-9242-28a72b0a7d52	cfd033b1-890f-4a2e-932f-b77f3400ec96
e220c7fb-27f9-448e-842b-50ab8a4959ec	ae32774b-1bed-4ab5-afa0-1ee0295fb9eb
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.web_origins (client_id, value) FROM stdin;
dd90a450-d5ef-4d13-b211-5a794446a37f	+
82806441-9195-44ea-a4e7-cbeb15c17556	+
f6148499-fe48-40cf-ac62-7d061c084312	/*
e3e3a6ba-b828-4ebd-9415-88988901eef1	/*
3854aed3-37b1-42c9-9bb0-4a40484449be	/*
\.


--
-- Name: org_domain ORG_DOMAIN_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org_domain
    ADD CONSTRAINT "ORG_DOMAIN_pkey" PRIMARY KEY (id, name);


--
-- Name: org ORG_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT "ORG_pkey" PRIMARY KEY (id);


--
-- Name: server_config SERVER_CONFIG_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.server_config
    ADD CONSTRAINT "SERVER_CONFIG_pkey" PRIMARY KEY (server_config_key);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: jgroups_ping constraint_jgroups_ping; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jgroups_ping
    ADD CONSTRAINT constraint_jgroups_ping PRIMARY KEY (address);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: revoked_token constraint_rt; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.revoked_token
    ADD CONSTRAINT constraint_rt PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: user_consent uk_external_consent; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_external_consent UNIQUE (client_storage_provider, external_client_id, user_id);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_local_consent; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_local_consent UNIQUE (client_id, user_id);


--
-- Name: migration_model uk_migration_update_time; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT uk_migration_update_time UNIQUE (update_time);


--
-- Name: migration_model uk_migration_version; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT uk_migration_version UNIQUE (version);


--
-- Name: org uk_org_alias; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_alias UNIQUE (realm_id, alias);


--
-- Name: org uk_org_group; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_group UNIQUE (group_id);


--
-- Name: org uk_org_name; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_name UNIQUE (realm_id, name);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: fed_user_attr_long_values; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fed_user_attr_long_values ON public.fed_user_attribute USING btree (long_value_hash, name);


--
-- Name: fed_user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fed_user_attr_long_values_lower_case ON public.fed_user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_att_by_name_value; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_client_att_by_name_value ON public.client_attributes USING btree (name, substr(value, 1, 255));


--
-- Name: idx_client_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_client_id ON public.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_composite ON public.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);


--
-- Name: idx_idp_for_login; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_idp_for_login ON public.identity_provider USING btree (realm_id, enabled, link_only, hide_on_login, organization_id);


--
-- Name: idx_idp_realm_org; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_idp_realm_org ON public.identity_provider USING btree (realm_id, organization_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);


--
-- Name: idx_offline_uss_by_broker_session_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_offline_uss_by_broker_session_id ON public.offline_user_session USING btree (broker_session_id, realm_id);


--
-- Name: idx_offline_uss_by_last_session_refresh; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_offline_uss_by_last_session_refresh ON public.offline_user_session USING btree (realm_id, offline_flag, last_session_refresh);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_org_domain_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_org_domain_org_id ON public.org_domain USING btree (org_id);


--
-- Name: idx_perm_ticket_owner; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_perm_ticket_owner ON public.resource_server_perm_ticket USING btree (owner);


--
-- Name: idx_perm_ticket_requester; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_perm_ticket_requester ON public.resource_server_perm_ticket USING btree (requester);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_rev_token_on_expire; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_rev_token_on_expire ON public.revoked_token USING btree (expire);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_usconsent_scope_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_usconsent_scope_id ON public.user_consent_client_scope USING btree (scope_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_email ON public.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);


--
-- Name: user_attr_long_values; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX user_attr_long_values ON public.user_attribute USING btree (long_value_hash, name);


--
-- Name: user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX user_attr_long_values_lower_case ON public.user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.13
-- Dumped by pg_dump version 15.13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

