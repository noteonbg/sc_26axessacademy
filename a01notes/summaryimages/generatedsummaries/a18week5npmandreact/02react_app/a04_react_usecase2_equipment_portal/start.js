/**
 * React Local Development Server (Clean & Robust)
 */
'use strict';

const http = require('http');
const fs = require('fs');
const path = require('path');

let PORT = 3002;

const MIME_TYPES = {
    '.html': 'text/html; charset=utf-8',
    '.js': 'text/javascript',
    '.jsx': 'text/javascript',
    '.css': 'text/css',
    '.json': 'application/json',
    '.png': 'image/png',
    '.jpg': 'image/jpeg'
};

function createServer(port) {
    const server = http.createServer((req, res) => {
        let safeUrl = req.url.split('?')[0];
        let filePath = path.join(__dirname, safeUrl === '/' ? 'index.html' : safeUrl);
        const ext = path.extname(filePath).toLowerCase();
        const contentType = MIME_TYPES[ext] || 'text/html; charset=utf-8';

        fs.readFile(filePath, (err, content) => {
            if (err) {
                fs.readFile(path.join(__dirname, 'index.html'), (error, defaultContent) => {
                    if (error) {
                        res.writeHead(500, { 'Content-Type': 'text/plain' });
                        res.end('Server Error: File not found');
                    } else {
                        res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
                        res.end(defaultContent);
                    }
                });
            } else {
                res.writeHead(200, { 'Content-Type': contentType });
                res.end(content);
            }
        });
    });

    server.on('error', (err) => {
        if (err.code === 'EADDRINUSE') {
            console.log(`Port ${port} is busy, switching to http://localhost:${port + 10}/...`);
            createServer(port + 10);
        } else {
            console.error('Server error:', err);
        }
    });

    server.listen(port, () => {
        console.log("=================================================");
        console.log("  React Local Development Server Running");
        console.log(`  Local URL: http://localhost:${port}/`);
        console.log("  Press Ctrl+C to stop.");
        console.log("=================================================");
    });
}

createServer(PORT);
