import React from 'react'
import { Navigate, Route, Routes, useLocation } from 'react-router-dom'
import LayoutHeader from '../layouts/LayoutHeader'
import HomePage from '../pages/HomePage'
import RegistroPage from '../pages/RegistroPage'

const RoutesApp = () => {

    const location = useLocation();

    const state = location.state;
    const backgroundLocation = state && state.backgroundLocation;

    return (
        <>
            <Routes location={backgroundLocation || location}>
                <Route path="/" element={<LayoutHeader />}>
                    {/* Ruta raíz redirige a /inicio */}
                    <Route index element={<Navigate to="/inicio" replace />} />

                    <Route path="inicio" element={<HomePage />} />
                    <Route path="registro" element={<RegistroPage />} />
                </Route>
            </Routes>
            {backgroundLocation && (
                <Routes>
                    <Route path="/registro" element={<RegistroPage />} />
                </Routes>
            )}
        </>
    )
}

export default RoutesApp