import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CustomerList from './CustomerList';

describe('CustomerList Component Unit Tests', () => {
  const mockCustomers = [
    { customerId: 1, name: 'Alice Johnson', email: 'alice@example.com', location: 'New York' },
    { customerId: 2, name: 'Bob Smith', email: 'bob@example.com', location: 'Chicago' },
  ];

  test('renders loading state when isLoading is true', () => {
    render(<CustomerList customers={[]} isLoading={true} onSelectCustomer={jest.fn()} onRefresh={jest.fn()} />);

    expect(screen.getByText('Loading customers from Spring Boot API...')).toBeInTheDocument();
  });

  test('renders empty state when customer list is empty and not loading', () => {
    render(<CustomerList customers={[]} isLoading={false} onSelectCustomer={jest.fn()} onRefresh={jest.fn()} />);

    expect(screen.getByText('No customers found.')).toBeInTheDocument();
  });

  test('renders customer table with read-only name column and customer details', () => {
    render(<CustomerList customers={mockCustomers} isLoading={false} onSelectCustomer={jest.fn()} onRefresh={jest.fn()} />);

    expect(screen.getByText('Customer Directory')).toBeInTheDocument();
    expect(screen.getByText('Name (Read-Only)')).toBeInTheDocument();
    expect(screen.getByText('Alice Johnson')).toBeInTheDocument();
    expect(screen.getByText('bob@example.com')).toBeInTheDocument();
    expect(screen.getByText('📍 New York')).toBeInTheDocument();
  });

  test('calls onSelectCustomer callback when Edit button is clicked', () => {
    const handleSelect = jest.fn();
    render(<CustomerList customers={mockCustomers} isLoading={false} onSelectCustomer={handleSelect} onRefresh={jest.fn()} />);

    const editButtons = screen.getAllByText('✏️ Edit');
    fireEvent.click(editButtons[0]);

    expect(handleSelect).toHaveBeenCalledWith(mockCustomers[0]);
  });

  test('calls onRefresh callback when Refresh button is clicked', () => {
    const handleRefresh = jest.fn();
    render(<CustomerList customers={mockCustomers} isLoading={false} onSelectCustomer={jest.fn()} onRefresh={handleRefresh} />);

    const refreshButton = screen.getByText('🔄 Refresh');
    fireEvent.click(refreshButton);

    expect(handleRefresh).toHaveBeenCalledTimes(1);
  });
});
