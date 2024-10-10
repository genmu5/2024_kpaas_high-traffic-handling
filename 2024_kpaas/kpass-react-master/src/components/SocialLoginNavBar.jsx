import React from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function ColorSchemesExample({ onSelectMenu }) {
    return (
        <Navbar bg="dark" data-bs-theme="dark">
            <Container>
                <Navbar.Brand onClick={() => onSelectMenu('home')} style={{ cursor: 'pointer' }}>
                    Navbar
                </Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link onClick={() => onSelectMenu('home')}>Home</Nav.Link>
                    <Nav.Link onClick={() => onSelectMenu('features')}>Features</Nav.Link>
                    <Nav.Link onClick={() => onSelectMenu('pricing')}>Pricing</Nav.Link>
                    <Nav.Link onClick={() => onSelectMenu('shelter')}>Shelters</Nav.Link> {/* 주변대피소 */}
                </Nav>
            </Container>
        </Navbar>
    );
}

export default ColorSchemesExample;
