import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import EditCustomerModal from './EditCustomerModal';

describe('EditCustomerModal Component Unit Tests', () => {
  const sampleCustomer = {
    customerId: 1,
    name: 'Alice Johnson',
    email: 'alice@example.com',
    location: 'New York',
  };

  test('does not render modal when isOpen is false', () => {
    const { container } = render(
      <EditCustomerModal customer={sampleCustomer} isOpen={false} onClose={jest.fn()} onUpdate={jest.fn()} />
    );

    expect(container.firstChild).toBeNull();
  });

  test('renders modal with read-only Customer ID and Name inputs disabled', () => {
    render(
      <EditCustomerModal customer={sampleCustomer} isOpen={true} onClose={jest.fn()} onUpdate={jest.fn()} />
    );

    expect(screen.getByText('Edit Customer Details')).toBeInTheDocument();

    const idInput = screen.getByLabelText(/Customer ID/i);
    const nameInput = screen.getByLabelText(/Customer Name/i);
    const emailInput = screen.getByLabelText(/Email Address/i);
    const locationInput = screen.getByLabelText(/Location/i);

    expect(idInput).toBeDisabled();
    expect(nameInput).toBeDisabled();
    expect(emailInput).not.toBeDisabled();
    expect(locationInput).not.toBeDisabled();

    expect(idInput.value).toBe('#1');
    expect(nameInput.value).toBe('Alice Johnson');
    expect(emailInput.value).toBe('alice@example.com');
    expect(locationInput.value).toBe('New York');
  });

  test('shows client-side validation errors when email or location are cleared', async () => {
    render(
      <EditCustomerModal customer={sampleCustomer} isOpen={true} onClose={jest.fn()} onUpdate={jest.fn()} />
    );

    const emailInput = screen.getByLabelText(/Email Address/i);
    fireEvent.change(emailInput, { target: { value: '' } });

    const submitBtn = screen.getByText('Save Changes');
    fireEvent.click(submitBtn);

    expect(await screen.findByText('Email is required')).toBeInTheDocument();
  });

  test('calls onUpdate with ONLY email and location on valid form submission', async () => {
    const handleUpdate = jest.fn().mockResolvedValueOnce({});
    const handleClose = jest.fn();

    render(
      <EditCustomerModal customer={sampleCustomer} isOpen={true} onClose={handleClose} onUpdate={handleUpdate} />
    );

    const emailInput = screen.getByLabelText(/Email Address/i);
    const locationInput = screen.getByLabelText(/Location/i);

    fireEvent.change(emailInput, { target: { value: 'alice.updated@example.com' } });
    fireEvent.change(locationInput, { target: { value: 'San Francisco' } });

    const submitBtn = screen.getByText('Save Changes');
    fireEvent.click(submitBtn);

    await waitFor(() => {
      expect(handleUpdate).toHaveBeenCalledWith(1, {
        email: 'alice.updated@example.com',
        location: 'San Francisco',
      });
      expect(handleClose).toHaveBeenCalled();
    });
  });
});
