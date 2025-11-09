# Pet Store API Automated Test Cases
This document describes all API test cases implemented in the Pet Store API automation framework.  
Each test validates different Pet Store API endpoints: Create, Retrieve (by ID & Status), Update, Delete, and End-to-End Lifecycle.

---

## Framework Overview

- **Base URL:** Configured in `env.properties`
- **Framework Components:**
  - `PetStoreService` – encapsulates REST API calls
  - `PetPayload` – POJO for request/response model
  - `RequestSpecBuilder` / `ResponseSpecBuilder` – centralized request & response expectations
  - `RetryAnalyzer` – retries flaky tests up to 3 times
- **Reporting:** Allure integrated (`@Epic`, `@Feature`, `@Story`, `@Description`)
- **Test Data:** Managed through `@DataProvider`
- **Tools:** Java • TestNG • RestAssured • Allure Reports

---

## Test Class: `AddPetServiceTest`

**Epic:** Pet Store Management API  
**Feature:** Add Pet Endpoint  
**Story:** Add a new pet  

### **Test Case: Add New Pet**
**Objective:** Verify that creating a new pet returns a `200` status and correct response body.

| **Test ID** | **TC_ADD_01** |
|--------------|----------------|
| **Request Type** | `POST /pet` |
| **Preconditions** | Pet Store API must be accessible |
| **Test Data** | 5 payloads via DataProvider |
| **Steps** | 1. Send `POST /pet` with each payload.<br>2. Validate status and response fields. |
| **Expected Result** | - Status code `200`.<br>- Response `name`, `status`, `category`, `tags` match payload.<br>- JSON response. |

---

## Test Class: `RetrievePetDataByIdServiceTest`

**Epic:** Pet Store Management API  
**Feature:** Get Pet Endpoint  
**Story:** Retrieve existing or non-existing pet  

### **Test Case: Retrieve Pet by Valid ID**
| **Test ID** | **TC_GET_01** |
|--------------|---------------|
| **Request Type** | `GET /pet/{id}` |
| **Test Data** | Valid IDs: 101, 102, 103, 104 |
| **Steps** | 1. Send GET request.<br>2. Verify status & ID. |
| **Expected Result** | - Status `200`.<br>- Returned ID matches request. |

---

### **Test Case: Retrieve Pet by Invalid ID**
| **Test ID** | **TC_GET_02** |
|--------------|---------------|
| **Request Type** | `GET /pet/{id}` |
| **Test Data** | Invalid ID: 000000000 |
| **Expected Result** | - Status `404`.<br>- Message: `"Pet not found"`. |

---

##  Test Class: `RetrievePetDataByStatusServiceTest`

**Epic:** Pet Store Management API  
**Feature:** Get Pet Endpoint  
**Story:** Retrieve pets by status  

### **Test Case: Retrieve Pets by Status**
**Objective:** Verify that retrieving pets by valid status returns a list with matching status values.

| **Test ID** | **TC_GET_03** |
|--------------|----------------|
| **Request Type** | `GET /pet/findByStatus?status={status}` |
| **Test Data** | `"available"`, `"pending"`, `"sold"` |
| **Steps** | 1. Send GET with given status.<br>2. Verify returned pets have the same status. |
| **Expected Result** | - Status code `200`.<br>- At least one item returned.<br>- `[0].status` equals query status.<br>- Response format is JSON. |

---

## Test Class: `UpdateExistingPetServiceTest`

**Epic:** Pet Store Management API  
**Feature:** Update Pet Endpoint  
**Story:** Update an existing pet  

### **Test Case: Update Existing Pet**
**Objective:** Verify that updating a pet returns `200` and reflects updated data.

| **Test ID** | **TC_UPDATE_01** |
|--------------|----------------|
| **Request Type** | `PUT /pet` |
| **Preconditions** | Pet must exist before update. |
| **Test Data** | Two payloads:<br> - ID 103 – add tag & photo URL<br> - ID 104 – change status to “available” |
| **Steps** | 1. Send PUT request with updated pet.<br>2. Validate response data. |
| **Expected Result** | - Status `200`.<br>- Updated data matches payload.<br>- Content type JSON. |

---

## Test Class: `DeletePetServiceTest`

**Epic:** Pet Store Management API  
**Feature:** Delete Pet Endpoint  
**Story:** Delete existing or non-existing pet  

### **Test Case: Delete Pet by Valid ID**
| **Test ID** | **TC_DELETE_01** |
|--------------|----------------|
| **Request Type** | `DELETE /pet/{id}` |
| **Test Data** | IDs: 101, 102, 103, 104 |
| **Steps** | 1. Send DELETE request.<br>2. Validate response code. |
| **Expected Result** | - Status code `200`.<br>- Deletion successful. |

---

### **Test Case: Delete Pet by Invalid ID**
| **Test ID** | **TC_DELETE_02** |
|--------------|----------------|
| **Request Type** | `DELETE /pet/{id}` |
| **Test Data** | Invalid ID: 000000000 |
| **Expected Result** | - Status code `404`.<br>- Error message indicates missing pet. |

---

## Test Class: `PetLifecycleTest`

**Epic:** Pet Store Management API  
**Feature:** Pet Lifecycle (E2E)  
**Story:** Validate full pet CRUD flow  

### **Test Case 1: Add New Pet**
| **Test ID** | **TC_LIFE_01** |
|--------------|----------------|
| **Description** | Create a new pet and validate all attributes. |
| **Expected Result** | Status `200`, correct name/status/category/tags. |

---

### **Test Case 2: Retrieve Created Pet**
| **Test ID** | **TC_LIFE_02** |
|--------------|----------------|
| **Description** | Retrieve the same pet and verify attributes. |
| **Expected Result** | Status `200`, details match creation payload. |

---

### **Test Case 3: Update Pet**
| **Test ID** | **TC_LIFE_03** |
|--------------|----------------|
| **Description** | Update existing pet details (photo URLs, tags, status). |
| **Expected Result** | Status `200`, updated data matches request body. |

---

### **Test Case 4: Retrieve Updated Pet**
| **Test ID** | **TC_LIFE_04** |
|--------------|----------------|
| **Description** | Verify the updated details persist correctly. |
| **Expected Result** | Status `200`, matches updated payload. |

---

### **Test Case 5: Delete Pet**
| **Test ID** | **TC_LIFE_05** |
|--------------|----------------|
| **Description** | Delete the pet and verify deletion response. |
| **Expected Result** | Status `200`, deletion successful. |

---

### **Test Case 6: Delete Non-existing Pet**
| **Test ID** | **TC_LIFE_06** |
|--------------|----------------|
| **Description** | Attempt to delete already deleted pet. |
| **Expected Result** | Status `404`, “Pet not found”. |

---

## Retry Mechanism

**Class:** `RetryAnalyzer`  
**Purpose:** Re-executes failed tests up to 3 times to mitigate transient API failures.

| **Parameter** | **Value** |
|----------------|------------|
| Max Retries | 3 |
| Delay per Retry | 15 seconds |
| Usage | Applied to lifecycle tests via `retryAnalyzer = api.utils.RetryAnalyzer.class` |

---

## Summary

| **Area** | **Endpoint** | **Test Cases** | **Coverage** |
|-----------|---------------|----------------|---------------|
| Add | `POST /pet` | 5 | ✅ |
| Retrieve by ID | `GET /pet/{id}` | 5 | ✅ |
| Retrieve by Status | `GET /pet/findByStatus` | 3 | ✅ |
| Update | `PUT /pet` | 2 | ✅ |
| Delete | `DELETE /pet/{id}` | 5 | ✅ |
| Lifecycle | Full CRUD flow | 6 | ✅ |
| **Total** |  | **26 Test Cases** | **Full CRUD Coverage Achieved** ✅ |

---

## Reporting and Execution

- **Framework supports:**  
  - Allure annotations for structured reports  
  - Request & response body attachments for traceability  
- **Execution Command:**
  ```bash
  mvn clean test
  allure serve allure-results