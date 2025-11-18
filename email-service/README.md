# Email Template Placeholders

This micro-service uses dynamic placeholders inside email templates. These placeholders are replaced at runtime with
real data.

---

### 1. **WELCOME_EMAIL Template**

| Placeholder | Description                            |
|-------------|----------------------------------------|
| `{{name}}`  | Name of the registered user            |
| `{{email}}` | Email address used during registration |

---

### 2. **PASSWORD_RESET_OTP Template**

| Placeholder | Description                                    |
|-------------|------------------------------------------------|
| `{{name}}`  | Name of the user requesting password reset     |
| `{{email}}` | Email address associated with the account      |
| `{{otp}}`   | One-Time Password generated for password reset |

---

### 3. **NOTIFICATION Template**

| Placeholder   | Description                                 |
|---------------|---------------------------------------------|
| `{{name}}`    | Name of the user receiving the notification |
| `{{message}}` | Notification message or content             |
