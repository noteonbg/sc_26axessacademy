import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import App from './App';

// Mock axios HTTP client
jest.mock('axios');

describe('App Component Integration Tests', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  test('Integration Test: Submits length=5 and breadth=4 and renders calculated result object', async () => {
    axios.post.mockResolvedValueOnce({
      data: {
        perimeter: 18,
        area: 20,
      },
    });

    render(<App />);

    const calculateBtn = screen.getByRole('button', { name: /Calculate PA/i });
    fireEvent.click(calculateBtn);

    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/rectangle/calculate', {
      length: 5,
      breadth: 4,
    });

    await waitFor(() => {
      expect(screen.getByText(/Calculated PA Response Object/i)).toBeInTheDocument();
      expect(screen.getByText('20')).toBeInTheDocument(); // Area
      expect(screen.getByText('18')).toBeInTheDocument(); // Perimeter
    });
  });

  test('Integration Test: Renders error banner when backend returns HTTP 400 ZeroDimensionsException', async () => {
    axios.post.mockRejectedValueOnce({
      response: {
        data: {
          message: 'Length and Breadth cannot both be zero!',
        },
      },
    });

    render(<App />);

    const testZeroBtn = screen.getByText(/Set Both to 0 \(Test Exception\)/i);
    fireEvent.click(testZeroBtn);

    const calculateBtn = screen.getByRole('button', { name: /Calculate PA/i });
    fireEvent.click(calculateBtn);

    await waitFor(() => {
      expect(screen.getByText(/Exception Handled:/i)).toBeInTheDocument();
      expect(screen.getByText('Length and Breadth cannot both be zero!')).toBeInTheDocument();
    });
  });
});
