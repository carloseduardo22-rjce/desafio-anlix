import { useEffect, useState } from "react";
import { loadPatients } from "../services/patientService";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

interface Patient {
  id: number;
  nome: string;
  cpf: string;
  listaIndicesCardiacos: { dataColeta: string; ind_card: string }[];
  listaIndicesPulmonares: { dataColeta: string; ind_pulm: string }[];
}

export default function PatientGraph() {
  const [patients, setPatients] = useState<Patient[]>([]);
  const [selectedPatient, setSelectedPatient] = useState<Patient | null>(null);
  const [selectedPatientId, setSelectedPatientId] = useState<string>("");
  const [selectedIndexType, setSelectedIndexType] = useState<string>("");

  useEffect(() => {
    async function fetchPatients() {
      const patientsData = await loadPatients();
      setPatients(patientsData);
    }
    fetchPatients();
  }, []);

  function handleAdvanceClick() {
    const patient = patients.find((p) => p.id === Number(selectedPatientId));
    if (patient) {
      setSelectedPatient(patient);
      setSelectedIndexType("");
    }
  }

  function handleIndexTypeClick(type: string) {
    setSelectedIndexType(type);
  }

  function getChartData() {
    if (!selectedPatient || !selectedIndexType) return [];

    const dataList =
      selectedIndexType === "cardiaco"
        ? selectedPatient.listaIndicesCardiacos
        : selectedPatient.listaIndicesPulmonares;

    return dataList
      .map((item) => {
        if ("ind_card" in item) {
          return {
            dataColeta: item.dataColeta.split("T")[0],
            valor: Number(item.ind_card),
          };
        } else if ("ind_pulm" in item) {
          return {
            dataColeta: item.dataColeta.split("T")[0],
            valor: Number(item.ind_pulm),
          };
        }
        return null;
      })
      .filter((item) => item !== null);
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-8 bg-gradient-to-b from-green-400 to-green-600">
      <h1 className="text-4xl font-bold text-white mb-8">
        Gráfico Temporal de Pacientes
      </h1>

      {!selectedPatient && (
        <div className="flex flex-col items-center gap-4 bg-white p-6 rounded-2xl shadow-md">
          <select
            value={selectedPatientId}
            onChange={(e) => setSelectedPatientId(e.target.value)}
            className="p-2 border rounded-lg text-gray-700"
          >
            <option value="">Selecione um paciente</option>
            {patients.map((patient) => (
              <option key={patient.id} value={patient.id}>
                {patient.nome} ({patient.cpf})
              </option>
            ))}
          </select>

          <button
            onClick={handleAdvanceClick}
            disabled={!selectedPatientId}
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded"
          >
            Avançar
          </button>
        </div>
      )}

      {selectedPatient && !selectedIndexType && (
        <div className="flex gap-6 mt-8">
          <button
            onClick={() => handleIndexTypeClick("cardiaco")}
            className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
          >
            Índice Cardíaco
          </button>
          <button
            onClick={() => handleIndexTypeClick("pulmonar")}
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
          >
            Índice Pulmonar
          </button>
        </div>
      )}

      {selectedPatient && selectedIndexType && (
        <div className="w-full h-[400px] mt-12 bg-white p-4 rounded-2xl shadow-md">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={getChartData()}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="dataColeta" />
              <YAxis />
              <Tooltip />
              <Line type="monotone" dataKey="valor" stroke="#8884d8" />
            </LineChart>
          </ResponsiveContainer>
        </div>
      )}
    </div>
  );
}
