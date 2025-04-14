import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import LatestIndexesPage from "./pages/LatestIndexesPages";
import ExportIndexesPage from "./pages/ExportIndexes";
import PatientGraph from "./pages/PatientGraph";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route
          path="/indicesRecentesPaciente"
          element={<LatestIndexesPage />}
        />
        <Route path="/exportarIndices" element={<ExportIndexesPage />} />
        <Route path="/graficoPaciente" element={<PatientGraph />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
