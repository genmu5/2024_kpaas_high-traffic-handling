import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button'

import {useNavigate} from "react-router-dom"

function ColorSchemesExample() {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate('/login');
    }
    return (
        <>
            <Navbar bg="dark" data-bs-theme="dark">
                <Container>
                    <Navbar.Brand href="#home">Navbar</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                        <Nav.Link href="#features">Features</Nav.Link>
                        <Nav.Link href="#pricing">Pricing</Nav.Link>
                    </Nav>
                    <Button variant='secondary m-1' onClick={handleClick}> 회원가입 </Button>
                    <Button variant='success m-1'> 로그인 </Button>
                </Container>
            </Navbar>
        </>
    );
}

export default ColorSchemesExample;