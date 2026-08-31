import apiClient from '../api/apiClient';
import { getAllCustomers, getCustomerById, updateCustomer } from './customerService';

// Mock the centralized Axios apiClient instance
jest.mock('../api/apiClient');

describe('customerService Unit Tests', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  test('getAllCustomers should call GET /customers and return data array', async () => {
    const mockCustomers = [
      { customerId: 1, name: 'Alice Johnson', email: 'alice@example.com', location: 'New York' },
    ];
    apiClient.get.mockResolvedValueOnce({ data: { success: true, data: mockCustomers } });

    const result = await getAllCustomers();

    expect(apiClient.get).toHaveBeenCalledWith('/customers');
    expect(result).toEqual(mockCustomers);
  });

  test('getCustomerById should call GET /customers/{id} and return customer object', async () => {
    const mockCustomer = { customerId: 1, name: 'Alice Johnson', email: 'alice@example.com', location: 'New York' };
    apiClient.get.mockResolvedValueOnce({ data: { success: true, data: mockCustomer } });

    const result = await getCustomerById(1);

    expect(apiClient.get).toHaveBeenCalledWith('/customers/1');
    expect(result).toEqual(mockCustomer);
  });

  test('updateCustomer should call PUT /customers/{id} sending ONLY email and location', async () => {
    const updatePayload = { email: 'alice.new@example.com', location: 'Boston' };
    const mockResponse = { success: true, message: 'Updated', data: { customerId: 1, name: 'Alice Johnson', ...updatePayload } };
    apiClient.put.mockResolvedValueOnce({ data: mockResponse });

    const result = await updateCustomer(1, updatePayload);

    expect(apiClient.put).toHaveBeenCalledWith('/customers/1', {
      email: 'alice.new@example.com',
      location: 'Boston',
    });
    expect(result).toEqual(mockResponse);
  });
});
