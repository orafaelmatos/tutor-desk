import React, { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { toast } from '@/hooks/use-toast';
import { studentApi } from '@/lib/api';
import { CreateStudentRequest } from '@/types/student';
import { UserPlus, ArrowLeft } from 'lucide-react';

const RegisterStudent = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  
  const [formData, setFormData] = useState<CreateStudentRequest>({
    name: '',
    email: '',
    phone: '',
    course: '',
    status: 'ACTIVE',
  });

  const [errors, setErrors] = useState<Partial<CreateStudentRequest>>({});

  const createMutation = useMutation({
    mutationFn: studentApi.createStudent,
    onSuccess: (newStudent) => {
      queryClient.invalidateQueries({ queryKey: ['students'] });
      toast({
        title: 'Success!',
        description: `Student ${newStudent.name} registered successfully`,
      });
      // Reset form
      setFormData({
        name: '',
        email: '',
        phone: '',
        course: '',
        status: 'ACTIVE',
      });
      setErrors({});
    },
    onError: (error) => {
      toast({
        title: 'Error',
        description: 'Failed to register student. Please try again.',
        variant: 'destructive',
      });
    },
  });

  const validateForm = (): boolean => {
    const newErrors: Partial<CreateStudentRequest> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    if (!formData.phone.trim()) {
      newErrors.phone = 'Phone number is required';
    }

    if (!formData.course.trim()) {
      newErrors.course = 'Course is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      createMutation.mutate(formData);
    }
  };

  const handleInputChange = (field: keyof CreateStudentRequest, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    // Clear error when user starts typing
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: undefined }));
    }
  };

  const predefinedCourses = [
    'Computer Science',
    'Software Engineering',
    'Data Science',
    'Web Development',
    'Mobile Development',
    'Machine Learning',
    'Cybersecurity',
    'UI/UX Design',
  ];

  return (
    <div className="container mx-auto px-6 py-8">
      <div className="max-w-2xl mx-auto space-y-8">
        {/* Header */}
        <div className="flex items-center space-x-4">
          <Button
            variant="outline"
            onClick={() => navigate('/')}
            className="border-border hover:bg-muted"
          >
            <ArrowLeft className="h-4 w-4 mr-2" />
            Back to Dashboard
          </Button>
          <div>
            <h1 className="text-3xl font-bold text-foreground mb-2">Register New Student</h1>
            <p className="text-muted-foreground">Add a new student to the management system</p>
          </div>
        </div>

        {/* Registration Form */}
        <Card className="bg-card border-border shadow-medium">
          <CardHeader className="bg-gradient-surface border-b border-border">
            <CardTitle className="flex items-center space-x-2 text-foreground">
              <UserPlus className="h-5 w-5 text-primary" />
              <span>Student Information</span>
            </CardTitle>
          </CardHeader>
          
          <CardContent className="pt-6">
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Name Field */}
              <div className="space-y-2">
                <Label htmlFor="name" className="text-foreground font-medium">
                  Full Name *
                </Label>
                <Input
                  id="name"
                  type="text"
                  placeholder="Enter student's full name"
                  value={formData.name}
                  onChange={(e) => handleInputChange('name', e.target.value)}
                  className={`bg-input border-border ${errors.name ? 'border-destructive' : ''}`}
                  required
                />
                {errors.name && (
                  <p className="text-sm text-destructive">{errors.name}</p>
                )}
              </div>

              {/* Email Field */}
              <div className="space-y-2">
                <Label htmlFor="email" className="text-foreground font-medium">
                  Email Address *
                </Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="student@example.com"
                  value={formData.email}
                  onChange={(e) => handleInputChange('email', e.target.value)}
                  className={`bg-input border-border ${errors.email ? 'border-destructive' : ''}`}
                  required
                />
                {errors.email && (
                  <p className="text-sm text-destructive">{errors.email}</p>
                )}
              </div>

              {/* Phone Field */}
              <div className="space-y-2">
                <Label htmlFor="phone" className="text-foreground font-medium">
                  Phone Number *
                </Label>
                <Input
                  id="phone"
                  type="tel"
                  placeholder="+1 (555) 123-4567"
                  value={formData.phone}
                  onChange={(e) => handleInputChange('phone', e.target.value)}
                  className={`bg-input border-border ${errors.phone ? 'border-destructive' : ''}`}
                  required
                />
                {errors.phone && (
                  <p className="text-sm text-destructive">{errors.phone}</p>
                )}
              </div>

              {/* Course Field */}
              <div className="space-y-2">
                <Label htmlFor="course" className="text-foreground font-medium">
                  Course *
                </Label>
                <div className="flex space-x-2">
                  <Select 
                    value={formData.course} 
                    onValueChange={(value) => handleInputChange('course', value)}
                  >
                    <SelectTrigger className={`bg-input border-border ${errors.course ? 'border-destructive' : ''}`}>
                      <SelectValue placeholder="Select a course" />
                    </SelectTrigger>
                    <SelectContent className="bg-popover border-border">
                      {predefinedCourses.map((course) => (
                        <SelectItem key={course} value={course}>
                          {course}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <div className="text-muted-foreground text-sm self-center">or</div>
                  <Input
                    placeholder="Custom course"
                    value={formData.course}
                    onChange={(e) => handleInputChange('course', e.target.value)}
                    className={`bg-input border-border ${errors.course ? 'border-destructive' : ''}`}
                  />
                </div>
                {errors.course && (
                  <p className="text-sm text-destructive">{errors.course}</p>
                )}
              </div>

              {/* Status Field */}
              <div className="space-y-2">
                <Label htmlFor="status" className="text-foreground font-medium">
                  Status
                </Label>
                <Select 
                  value={formData.status} 
                  onValueChange={(value: 'ACTIVE' | 'INACTIVE') => handleInputChange('status', value)}
                >
                  <SelectTrigger className="bg-input border-border">
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent className="bg-popover border-border">
                    <SelectItem value="ACTIVE">Active</SelectItem>
                    <SelectItem value="INACTIVE">Inactive</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              {/* Submit Button */}
              <div className="pt-4 border-t border-border">
                <div className="flex space-x-4">
                  <Button
                    type="submit"
                    disabled={createMutation.isPending}
                    variant="gradient"
                    className="flex-1"
                  >
                    {createMutation.isPending ? (
                      <div className="flex items-center space-x-2">
                        <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white"></div>
                        <span>Registering...</span>
                      </div>
                    ) : (
                      <div className="flex items-center space-x-2">
                        <UserPlus className="h-4 w-4" />
                        <span>Register Student</span>
                      </div>
                    )}
                  </Button>
                  
                  <Button
                    type="button"
                    variant="outline"
                    onClick={() => {
                      setFormData({
                        name: '',
                        email: '',
                        phone: '',
                        course: '',
                        status: 'ACTIVE',
                      });
                      setErrors({});
                    }}
                    disabled={createMutation.isPending}
                    className="border-border hover:bg-muted"
                  >
                    Clear Form
                  </Button>
                </div>
              </div>
            </form>
          </CardContent>
        </Card>

        {/* Helper Text */}
        <Card className="bg-muted/30 border-border">
          <CardContent className="pt-6">
            <div className="text-sm text-muted-foreground space-y-2">
              <p className="font-medium">Registration Notes:</p>
              <ul className="list-disc list-inside space-y-1 ml-4">
                <li>All fields marked with * are required</li>
                <li>Email addresses must be unique for each student</li>
                <li>Students can be activated or deactivated after registration</li>
                <li>Course names are case-sensitive</li>
              </ul>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default RegisterStudent;