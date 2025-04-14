import { Activity, Search, Users } from "lucide-react";
import { useState } from "react";
import { searchPatientByName, loadPatients } from "../services/patientService";
import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const navigate = useNavigate();
  const [showInput, setShowInput] = useState(false);
  const [patientName, setPatientName] = useState("");

  function handleInputSearchPatient() {
    setShowInput(true);
  }

  async function handleLoadPatients() {
    const data = await loadPatients();
    navigate("/exportarIndices", { state: { patients: data } });
  }

  async function handleSearchPatient() {
    const data = await searchPatientByName(patientName);
    navigate("/indicesRecentesPaciente", { state: { patient: data } });
  }

  function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
    setPatientName(event.target.value);
  }

  function handlePatientGraph() {
    navigate("/graficoPaciente");
  }

  return (
    <div className="flex items-center justify-center h-screen bg-gradient-to-b from-green-400 to-green-600">
      <div className="flex flex-col space-y-5 bg-white/10 backdrop-blur-sm p-16 rounded-xl shadow-lg">
        <h1 className="text-5xl font-bold text-white text-center mb-10">
          Sistema Clínico
        </h1>

        {!showInput ? (
          <button
            className="flex items-center justify-center gap-3 p-5 bg-white rounded-lg text-green-700 font-medium shadow-md hover:bg-green-50 transition-all"
            onClick={handleInputSearchPatient}
          >
            <Search className="h-5 w-5" />
            Buscar paciente
          </button>
        ) : (
          <div className="flex items-center gap-3">
            <input
              type="text"
              placeholder="Digite o nome do paciente"
              className="p-3 rounded-lg shadow-md focus:outline-none border border-white"
              value={patientName}
              onChange={handleInputChange}
            />
            <button
              onClick={handleSearchPatient}
              className="p-3 bg-green-700 text-white rounded-lg shadow-md hover:bg-green-800 transition-all"
            >
              Buscar
            </button>
          </div>
        )}

        <button
          className="flex items-center justify-center gap-3 p-5 bg-white rounded-lg text-green-700 font-medium shadow-md hover:bg-green-50 transition-all"
          onClick={handleLoadPatients}
        >
          <Users className="h-5 w-5" />
          Pacientes
        </button>

        <button
          className="flex items-center justify-center gap-3 p-5 bg-white rounded-lg text-green-700 font-medium shadow-md hover:bg-green-50 transition-all"
          onClick={handlePatientGraph}
        >
          <Activity className="h-5 w-5" />
          Gráfico
        </button>
      </div>
    </div>
  );
}
