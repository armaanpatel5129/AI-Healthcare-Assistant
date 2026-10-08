# Database Design

## Why MySQL?
MySQL is a relational database. Our data (users, profiles, symptoms, results)
is connected, so tables with relationships fit well.

## Tables
1. users - stores login details (name, email, password).
2. health_profiles - one profile per user (age, height, allergies, etc.).
3. symptom_records - every time a user enters symptoms, one row is saved.
4. analysis_results - the analyzer's answer (risk level, guidance, warning) for one symptom record.

## Relationships
- users -> health_profiles (one user has one profile)
- users -> symptom_records (one user has many symptom records)
- symptom_records -> analysis_results (one symptom record has one result)
## Foreign Keys
- health_profiles.user_id -> users.id
- symptom_records.user_id -> users.id
- analysis_results.symptom_record_id -> symptom_records.id
ON DELETE CASCADE is used, so deleting a user also deletes their related data.

## History Query
History is not a separate table. It is created by joining symptom_records and analysis_results:

SELECT s.symptoms, s.severity, a.risk_level, a.guidance, s.created_at
FROM symptom_records s
JOIN analysis_results a ON a.symptom_record_id = s.id
WHERE s.user_id = 1
ORDER BY s.created_at DESC;