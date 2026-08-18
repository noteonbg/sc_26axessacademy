import { render, screen } from '@testing-library/react';
import App from './App';

test('renders enterprise modular react application heading', () => {
  render(<App />);
  const headingElement = screen.getByText(/6-Developer Enterprise React Folder Architecture/i);
  expect(headingElement).toBeInTheDocument();
});
