import React, { useState } from 'react';

export function AnalyticsModule({ appMetrics }) {
  const [metrics] = useState(
    appMetrics || {
      activeUsers: 1420,
      dailySales: '$12,450.00',
      systemHealth: '99.98% Uptime',
      activeFeatureStories: 6,
      developerTeamSize: 6,
    }
  );

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2>📊 Parent Component: AnalyticsModule (Developer 6 Domain)</h2>
        <span style={styles.badge}>Developer 6</span>
      </div>

      <div style={styles.metricsGrid}>
        <div style={styles.metricCard}>
          <span style={styles.metricTitle}>Active Team Developers</span>
          <h3 style={styles.metricValue}>{metrics.developerTeamSize} Engineers</h3>
        </div>

        <div style={styles.metricCard}>
          <span style={styles.metricTitle}>Isolated Feature Slices</span>
          <h3 style={styles.metricValue}>{metrics.activeFeatureStories} Stories</h3>
        </div>

        <div style={styles.metricCard}>
          <span style={styles.metricTitle}>Gross Sales Volume</span>
          <h3 style={styles.metricValue}>{metrics.dailySales}</h3>
        </div>

        <div style={styles.metricCard}>
          <span style={styles.metricTitle}>System Stability</span>
          <h3 style={styles.metricValue}>{metrics.systemHealth}</h3>
        </div>
      </div>

      <div style={styles.logCard}>
        <h4>📈 System Real-time Data Object:</h4>
        <pre style={styles.json}>{JSON.stringify(metrics, null, 2)}</pre>
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#f0f9ff',
    padding: '1.5rem',
    borderRadius: '10px',
    border: '2px solid #0284c7',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '1rem',
  },
  badge: {
    backgroundColor: '#e0f2fe',
    color: '#0369a1',
    padding: '0.35rem 0.75rem',
    borderRadius: '12px',
    fontSize: '0.8rem',
    fontWeight: 'bold',
  },
  metricsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
    gap: '1rem',
    marginBottom: '1rem',
  },
  metricCard: {
    backgroundColor: '#ffffff',
    padding: '1rem',
    borderRadius: '8px',
    border: '1px solid #bae6fd',
  },
  metricTitle: {
    fontSize: '0.8rem',
    color: '#64748b',
  },
  metricValue: {
    margin: '0.5rem 0 0 0',
    color: '#0369a1',
  },
  logCard: {
    backgroundColor: '#0f172a',
    color: '#38bdf8',
    padding: '1rem',
    borderRadius: '6px',
  },
  json: {
    margin: 0,
    fontSize: '0.85rem',
  },
};
