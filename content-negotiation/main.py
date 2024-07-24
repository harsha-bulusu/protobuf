from http.server import BaseHTTPRequestHandler, HTTPServer
import json

class RequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        # Parse Accept header
        accept_header = self.headers.get('Accept', '')

        if 'application/json' in accept_header:
            self.respond_with_json()
        elif 'text/html' in accept_header:
            self.respond_with_html()
        else:
            self.respond_with_text()

    def respond_with_json(self):
        content = {
            'message': 'Hello, this is a JSON response',
            'status': 'success'
        }
        response = json.dumps(content)
        self.send_response(200)
        self.send_header('Content-Type', 'application/json')
        self.send_header('Content-Length', str(len(response)))
        self.end_headers()
        self.wfile.write(response.encode('utf-8'))

    def respond_with_html(self):
        content = """
        <html>
        <head><title>Hello</title></head>
        <body><h1>Hello, this is an HTML response</h1></body>
        </html>
        """
        self.send_response(200)
        self.send_header('Content-Type', 'text/html')
        self.send_header('Content-Length', str(len(content)))
        self.end_headers()
        self.wfile.write(content.encode('utf-8'))

    def respond_with_text(self):
        content = 'Hello, this is a plain text response'
        self.send_response(200)
        self.send_header('Content-Type', 'text/plain')
        self.send_header('Content-Length', str(len(content)))
        self.end_headers()
        self.wfile.write(content.encode('utf-8'))

def run(server_class=HTTPServer, handler_class=RequestHandler, port=8080):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f'Starting server on port {port}...')
    httpd.serve_forever()

if __name__ == '__main__':
    run()
