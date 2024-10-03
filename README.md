# The drone

## Description
Drone delivery system.

## Installation
git clone https://github.com/khalifax2/drone.git

open folder in IDE (Intellij)

Import json collection to Postman filename: THE DRONE.postman_collection.json

## Default Database Initialization

When the application starts, it will automatically run the following SQL scripts to populate the `drone` and `medication` tables with default data.

## H2 console
url: http://localhost:8080/api/v1/h2-console

jdbc-url: jdbc:h2:mem:dronedb



### SQL Scripts

```sql
-- Clear existing data from drone and medication tables
DELETE FROM drone;

INSERT INTO drone
(drone_id, battery_capacity, model, serial_no, state, weight_limit) VALUES
('drone-lightweight-1', 100, 'LIGHTWEIGHT', '001', 'IDLE', 100),
('drone-middletweight-2', 100, 'MIDDLEWEIGHT', '002', 'IDLE', 200),
('drone-cruiserweight-3', 100, 'CRUISERWEIGHT', '003', 'IDLE', 500),
('drone-heavyweight-4', 100, 'HEAVYWEIGHT', '004', 'IDLE', 1000);

DELETE FROM medication;

INSERT INTO medication (medication_id, code, image_path, name, weight, drone_id) VALUES
('id-paracetamol', 'MED_1', '/img1', 'PARACETAMOL', 10, NULL),
('id-ibuprofen', 'MED_2', '/img2', 'IBUPROFEN', 10, NULL),
('id-norgesic', 'MED_3', '/img1', 'NORGESIC', 20, NULL),
('id-twynsta', 'MED_4', '/img2', 'TWYNSTA', 20, NULL),
('id-amoxicillin', 'MED_5', '/img3', 'AMOXICILLIN', 50, NULL),
('id-atorvastatin', 'MED_6', '/img4', 'ATORVASTATIN', 50, NULL),
('id-metmorfin', 'MED_7', '/img5', 'METFORMIN', 100, NULL),
('id-aspirin', 'MED_8', '/img6', 'ASPIRIN', 100, NULL),
('id-omeprazole', 'MED_9', '/img7', 'OMEPRAZOLE', 200, NULL),
('id-lisinopril', 'MED_10', '/img8', 'LISINOPRIL', 200, NULL),
('id-albuterol', 'MED_11', '/img9', 'ALBUTEROL', 500, NULL),
('id-prednisone', 'MED_12', '/img10', 'PREDNISONE', 500, NULL);

```

# API Documentation

### Base URL
/api/v1

### Endpoints

#### 1. Add a Single Medication

- **URL**: `/api/v1/medication/add`
- **Method**: `POST`
- **Request Body**: `MedicationDto`
- **Description**: Add a new medication to the system.

**Request Format**:
```json
{
  "name": "MEDICINE_X",
  "weight": 10,
  "code": "MED_1",
  "imagePath": "/img1"
}
```

#### 2. Add Multiple Medications

- **URL**: `/api/v1/medication/addAll`
- **Method**: `POST`
- **Request Body**: `List<MedicationDto>`
- **Description**: Adds multiple medications to the system.

**Request Format**:
```json
[
  {
    "name": "IBUPROFEN",
    "weight": 10,
    "code": "MED_2",
    "imagePath": "/img2"
  },
  {
    "name": "AMOXICILLIN",
    "weight": 50,
    "code": "MED_3",
    "imagePath": "/img3"
  }
]
```

**Validation**:
```aidl
name:      Must not be blank and can only contain letters, numbers, hyphens, or underscores.
weight:    Must not be null and must be a number (up to 999999).
code:      Must not be blank and can only contain uppercase letters, underscores, or numbers.
imagePath: Must not be blank.
```

**Response**:
```json
Status Code: 201 Created
Response Body: "Added successfully."
```

#### 2. Register a Drone

- **URL**: `/api/v1/drone/register`
- **Method**: `POST`
- **Request Body**: `DroneDto`
- **Description**: Registers a new drone in the system.

**Request Format**:
```json
{
  "serialNo": "200",
  "model": "LIGHTWEIGHT"
}
```

**Validation**:
```aidl
serialNo: Must not be blank and unique with a maximum length of 100 characters.
model:    Optional field representing the drone model.
```

**Response**:
```json
Status Code: 201 Created
Response Body: "Added successfully."
```

#### 3. Load Medication onto Drone
- **URL**: `/api/v1/loading/loadMedication`
- **Method**: `POST`
- **Request Body**: `LoadingMedDto`
- **Description**: Loads medications onto the specified drone.

**Request Format**:
```json
{
  "droneId": "drone-id-1",
  "medicationIds": ["medication-id-1", "medication-id-2"]
}
```
**Validation**:
```aidl
- droneId: Must not be blank.
- medicationIds: List of medication IDs to be loaded onto the drone.
- Error if Drone Not Available
- Error if Medication Already Loaded
- Battery Level Too Low
- Exceeding Weight Limit
```

**Response**:
```json
Status Code: 200 OK
Response Body: "Loaded successfully."
```


#### 4. Get Loaded Medication for a Drone

- **URL**: `/api/v1/drone/{droneId}/loadedMedication`
- **Method**: `GET`
- **Description**: Retrieves a list of medications loaded on the specified drone.
- **Path Variable**: droneId: The ID of the drone.

**Response**:
```json
[
  {
    "name": "PARACETAMOL",
    "weight": 10,
    "code": "MED_1",
    "imagePath": "/img1"
  },
  ...
]
```


#### 5. Get Drone Battery Information

- **URL**: `/api/v1/drone/batteryInfo`
- **Method**: `GET`
- **Description**: Retrieves the battery level of the specified drone.
- **Request Parameter**: droneId

**Response**:
```json
Status Code: 200 OK
Response Body: "Drone battery level: 100%"
```

#### 6. Check Drone Availability

- **URL**: `/api/v1/drone/availability`
- **Method**: `GET`
- **Description**: Checks the availability status of the specified drone based on its battery level and state.
- **Request Parameter**: droneId

**Response**:
```json
Status Code: 200 OK
Response Body: "Drone is available for loading."
```

#### 7. Start Drone Scheduler

- **URL**: `/api/v1/drone/scheduler`
- **Method**: `GET`
- **Description**: Starts the scheduler for the specified drone.
- **Request Parameter**: droneId

**Behaviour**:
```aidl
Drone States: LOADED, DELIVERING, DELIVERED, RETURNING.
For each state, there will be a 2-second delay.
For every successful delivery, the battery will be reduced by 50%.
```
**Validation**:
```aidl
- Scheduler will not start if and will throw an error:
    - Specified drone already running.
    - Specified drone state is not "LOADED"
    - Specified drone battery level is below 25%
```


**Response**:
```json
Status Code: 200 OK
Response Body: "Drone scheduler started"
```
