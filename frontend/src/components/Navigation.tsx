import { Link, useLocation } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Users, UserPlus, GraduationCap } from 'lucide-react';

export function Navigation() {
  const location = useLocation();
  
  const isActive = (path: string) => location.pathname === path;
  
  return (
    <nav className="bg-card border-b border-border shadow-soft">
      <div className="container mx-auto px-6 py-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2">
            <div className="p-2 bg-gradient-primary rounded-lg">
              <GraduationCap className="h-6 w-6 text-white" />
            </div>
            <h1 className="text-xl font-bold text-foreground">
              Tutor Desk
            </h1>
          </div>
          
          <div className="flex items-center space-x-4">
            <Button
              variant={isActive('/') ? 'gradient' : 'ghost'}
              asChild
            >
              <Link to="/" className="flex items-center space-x-2">
                <Users className="h-4 w-4" />
                <span>Dashboard</span>
              </Link>
            </Button>
            
            <Button
              variant={isActive('/register') ? 'gradient' : 'ghost'}
              asChild
            >
              <Link to="/register" className="flex items-center space-x-2">
                <UserPlus className="h-4 w-4" />
                <span>Cadastro de Aluno</span>
              </Link>
            </Button>
          </div>
        </div>
      </div>
    </nav>
  );
}