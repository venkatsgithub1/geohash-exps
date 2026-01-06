# What
Java maven project to experiment with geohashing
# H3 service usage
For example use `http://localhost:8082/v1/h3?latitude=12.973801&longitude=77.611885`, to figure out nearby venues using h3 service
# TODO
- Update code to work with geo hashes with the help of a service
- Find a way to let the client send the level of range they want to scan.
- See if we can have a h3IndexRes7 that can store h3 indexes with resolution 7 along side existing resolution 9 column, so that we can scan bigger hexagons.
# Improvements
- A better design is to have a background job like airflow DAG that can ingest places incrementally into DB.
- A DB outside service other than H2. For the purposes of an experiment, internal DB is fine.
- Add caffeine cache to services.