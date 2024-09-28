import React, { useRef, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import useCurrentLocation from '../../../hooks/useCurrentLocation';
import { currentLocationAtom } from '../../../state/currentLocationAtom';

const icons = [
    { id: 1, name: '지진', icon: '🌍' },
    { id: 2, name: '태풍', icon: '🌀' },
    { id: 3, name: '폭염', icon: '☀️' },
    { id: 4, name: '한파', icon: '❄️' },
    { id: 5, name: '호우', icon: '🌧️' },
    { id: 6, name: '산불', icon: '🔥' },
];

const IssueContainer = () => {
    useCurrentLocation();
    const currentLocation = useRecoilValue(currentLocationAtom);
    const mapRef = useRef(null);
    //const [shelters, setShelters] = useState([]);
    const [activeIcon, setActiveIcon] = useState(null);
    //const markersRef = useRef([]);
    const [activeInfoWindows, setActiveInfoWindows] = useState([]); // 활성화된 정보창 저장용

    useEffect(() => {
        const naverMapClientId = process.env.REACT_APP_NAVER_MAP_CLIENT_ID;

        const script = document.createElement('script');
        script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${naverMapClientId}`;
        script.async = true;
        document.head.appendChild(script);

        script.onload = () => {
            if (window.naver) {
                const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833);
                mapRef.current = new window.naver.maps.Map('map', {
                    center: defaultCenter,
                    zoom: 7,
                    minZoom: 6,
                    zoomControl: true,
                    mapTypeControl: true,
                    zoomControlOptions: {
                        position: window.naver.maps.Position.TOP_RIGHT,
                    },
                    mapDataControl: false,
                });
            }
        };

        return () => {
            document.head.removeChild(script);
        };
    }, []);
    // currentLocation 변경 시 지도를 부드럽게 이동
    useEffect(() => {
        if (mapRef.current && currentLocation.lat && currentLocation.lng) {
            const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
            mapRef.current.panTo(targetLocation, { duration: 1000 });
            mapRef.current.setZoom(13, { animate: true, duration: 1000 });
        }
    }, [currentLocation]);

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
            <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>IssueContainer</div>
            <div style={{ display: 'flex', justifyContent: 'center', margin: '10px 0' }}>
                {icons.map((icon) => (
                    <div
                        key={icon.id}
                        style={{
                            margin: '0 10px',
                            textAlign: 'center',
                            cursor: 'pointer',
                            color: icon.id === activeIcon ? 'blue' : 'black'
                        }}
                        onClick={() => handleIconClick(icon.id)}
                    >
                        <span>{icon.icon}</span>
                        <br />
                        <span>{icon.name}</span>
                    </div>
                ))}
            </div>
            <div id="map" style={{ width: '650px', height: '500px' }}></div>
        </div>
    );
};

export default IssueContainer;
