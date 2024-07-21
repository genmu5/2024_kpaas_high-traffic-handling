import React from 'react';
import { MapContainer, TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const icons = [
    { id: 1, name: '지진', icon: '🌍' },
    { id: 2, name: '태풍', icon: '🌀' },
    { id: 3, name: '폭염', icon: '☀️' },
    { id: 4, name: '한파', icon: '❄️' },
    { id: 5, name: '호우', icon: '🌧️' },
    { id: 6, name: '산불', icon: '🔥' },
];

const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
};

const iconBarStyle = {
    display: 'flex',
    justifyContent: 'center',
    margin: '10px 0',
};

const iconItemStyle = {
    margin: '0 10px',
    textAlign: 'center',
};

const mapContainerStyle = {
    flex: 1,
    width: '650px',
    height: '100%', // Adjust the height as needed
};

const mapStyle = {
    width: '100%',
    height: '100%',
};

const IssueContainer = () => {
    return (
        <div style={containerStyle}>
            <div style={iconBarStyle}>
                {icons.map((icon) => (
                    <div key={icon.id} style={iconItemStyle}>
                        <span>{icon.icon}</span>
                        <br />
                        <span>{icon.name}</span>
                    </div>
                ))}
            </div>
            <div style={mapContainerStyle}>
                <MapContainer center={[36.5, 128]} zoom={7.3} style={mapStyle}>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    />
                </MapContainer>
            </div>
        </div>
    );
};

export default IssueContainer;
