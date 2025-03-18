CREATE TABLE resume (
                        uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        full_name VARCHAR(100) NOT NULL
);

CREATE TABLE contacts (
                          id SERIAL PRIMARY KEY,
                          resume_uuid UUID REFERENCES resume(uuid) ON DELETE CASCADE,
                          contact_type VARCHAR(50) NOT NULL,
                          contact_value TEXT NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index ON contacts (resume_uuid, contact_type);

CREATE TABLE sections (
                          id SERIAL PRIMARY KEY,
                          resume_uuid UUID REFERENCES resume(uuid) ON DELETE CASCADE,
                          section_type VARCHAR(50) NOT NULL,
                          section_content TEXT NOT NULL
);