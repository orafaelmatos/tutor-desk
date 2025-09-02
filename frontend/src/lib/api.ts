import { Student, CreateStudentRequest, UpdateSubscriptionRequest } from '@/types/student';

const API_BASE_URL = 'http://localhost:8080/api';

class ApiError extends Error {
  constructor(public status: number, message: string) {
    super(message);
    this.name = 'ApiError';
  }
}

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;
  
  const response = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    throw new ApiError(response.status, `API Error: ${response.statusText}`);
  }

  return response.json();
}

export const studentApi = {
  // GET /students - Fetch all students
  getAllStudents: (): Promise<Student[]> =>
    request<Student[]>('/students'),

  // POST /students - Create a new student
  createStudent: (studentData: CreateStudentRequest): Promise<Student> =>
    request<Student>('/students', {
      method: 'POST',
      body: JSON.stringify(studentData),
    }),

  // PUT /students/{id}/subscription - Update student subscription
  updateSubscription: (id: number, data: UpdateSubscriptionRequest): Promise<Student> =>
    request<Student>(`/students/${id}/subscription`, {
      method: 'PUT',
      body: JSON.stringify(data),
    }),

  // DELETE /students/{id} - Delete a student
  deleteStudent: (id: number): Promise<void> =>
    request<void>(`/students/${id}`, {
      method: 'DELETE',
    }),
};