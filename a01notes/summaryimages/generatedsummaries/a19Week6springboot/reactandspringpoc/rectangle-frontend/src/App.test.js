import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import App from './App';

describe('App Component Unit Tests', () => {
  test('Unit Test: Renders calculation title and default input fields', () => {
    render(<App />);

    expect(screen.getByText(/Rectangle PA Calculator/i)).toBeInTheDocument();
    
    const lengthInput = screen.getByLabelText(/Length:/i);
    const breadthInput = screen.getByLabelText(/Breadth:/i);

    expect(lengthInput.value).toBe('5');
    expect(breadthInput.value).toBe('4');
  });

  test('Unit Test: Updates input values when user types', () => {
    render(<App />);

    const lengthInput = screen.getByLabelText(/Length:/i);
    const breadthInput = screen.getByLabelText(/Breadth:/i);

    fireEvent.change(lengthInput, { target: { value: '10' } });
    fireEvent.change(breadthInput, { target: { value: '6' } });

    expect(lengthInput.value).toBe('10');
    expect(breadthInput.value).toBe('6');
  });

  test('Unit Test: Sets both inputs to 0 when test button is clicked', () => {
    render(<App />);

    const testZeroBtn = screen.getByText('Set Both to 0 (Test Exception)');
    fireEvent.click(testZeroBtn);

    const lengthInput = screen.getByLabelText(/Length:/i);
    const breadthInput = screen.getByLabelText(/Breadth:/i);

    expect(lengthInput.value).toBe('0');
    expect(breadthInput.value).toBe('0');
  });
});
