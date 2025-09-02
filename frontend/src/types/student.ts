export interface Student {
  id: number;
  name: string;
  email: string;
  phone: string;
  course: string;
  status: 'ACTIVE' | 'INACTIVE';
  progressEntries?: ProgressEntry[];
}

export interface ProgressEntry {
  id: number;
  topic: string;
  completed: boolean;
  completedDate?: string;
}

export interface CreateStudentRequest {
  name: string;
  email: string;
  phone: string;
  course: string;
  status: 'ACTIVE' | 'INACTIVE';
}

export interface UpdateSubscriptionRequest {
  status: 'ACTIVE' | 'INACTIVE';
}