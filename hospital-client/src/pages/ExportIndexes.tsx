import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import {
  CheckSquare,
  Download,
  FileDown,
  Search,
  Square,
  User,
  ArrowLeft,
} from "lucide-react";

export default function ExportIndexesPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { patients } = location.state;

  const [selectedPatients, setSelectedPatients] = useState<number[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredPatients, setFilteredPatients] = useState(patients || []);

  useEffect(() => {
    if (!patients) return;

    const filtered = patients.filter(
      (patient: any) =>
        patient.nome.toLowerCase().includes(searchTerm.toLowerCase()) ||
        patient.cpf.includes(searchTerm)
    );
    setFilteredPatients(filtered);
  }, [searchTerm, patients]);

  const togglePatient = (patientId: number) => {
    if (selectedPatients.includes(patientId)) {
      setSelectedPatients(selectedPatients.filter((id) => id !== patientId));
    } else {
      setSelectedPatients([...selectedPatients, patientId]);
    }
  };

  const toggleAll = () => {
    if (selectedPatients.length === filteredPatients.length) {
      setSelectedPatients([]);
    } else {
      setSelectedPatients(filteredPatients.map((patient: any) => patient.id));
    }
  };

  const exportIndices = () => {
    let csvContent =
      "Paciente CPF,Nome,Tipo Índice,ID Índice,Epoch,Data Coleta,Valor\n";

    selectedPatients.forEach((patientId) => {
      const patient = patients.find((p: any) => p.id === patientId);

      if (!patient) return;

      patient.listaIndicesPulmonares?.forEach((index: any) => {
        csvContent += `${patient.cpf},"${patient.nome}",Pulmonar,${index.id},${index.epoch},${index.dataColeta},${index.ind_pulm}\n`;
      });

      patient.listaIndicesCardiacos?.forEach((index: any) => {
        csvContent += `${patient.cpf},"${patient.nome}",Cardíaco,${index.id},${index.epoch},${index.dataColeta},${index.ind_card}\n`;
      });
    });

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    const url = URL.createObjectURL(blob);
    link.setAttribute("href", url);
    link.setAttribute("download", "indices_pacientes.csv");
    link.style.visibility = "hidden";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-green-400 to-green-600 p-4 md:p-8">
      <button
        onClick={() => navigate(-1)}
        className="p-2 rounded hover:bg-gray-100"
      >
        <ArrowLeft className="h-6 w-6" />
      </button>
      <div className="max-w-5xl mx-auto">
        <div className="bg-white rounded-t-xl shadow-lg p-6">
          <div className="flex flex-col md:flex-row md:items-center justify-between">
            <div className="flex items-center gap-4">
              <div className="bg-green-100 p-3 rounded-full">
                <FileDown className="h-8 w-8 text-green-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-800">
                  Exportar Índices
                </h1>
                <p className="text-gray-500">
                  Selecione os pacientes para exportar seus índices
                </p>
              </div>
            </div>
            <div className="mt-4 md:mt-0">
              <button
                onClick={exportIndices}
                disabled={selectedPatients.length === 0}
                className={`flex items-center gap-2 px-4 py-2 rounded-lg text-white ${
                  selectedPatients.length > 0
                    ? "bg-green-600 hover:bg-green-700"
                    : "bg-gray-400 cursor-not-allowed"
                }`}
              >
                <Download className="h-5 w-5" />
                Exportar{" "}
                {selectedPatients.length > 0 && `(${selectedPatients.length})`}
              </button>
            </div>
          </div>
        </div>

        <div className="bg-white p-4 border-t border-b border-gray-200 flex flex-col sm:flex-row gap-4 items-center justify-between">
          <div className="relative w-full sm:w-auto">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
            <input
              type="text"
              placeholder="Buscar paciente por nome ou CPF..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg w-full sm:w-80 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <button
            onClick={toggleAll}
            className="flex items-center gap-2 text-green-700 hover:text-green-800"
          >
            {selectedPatients.length === filteredPatients.length ? (
              <>
                <CheckSquare className="h-5 w-5" />
                Desmarcar todos
              </>
            ) : (
              <>
                <Square className="h-5 w-5" />
                Selecionar todos
              </>
            )}
          </button>
        </div>

        <div className="bg-white rounded-b-xl shadow-lg overflow-hidden">
          {filteredPatients.length > 0 ? (
            <div className="divide-y divide-gray-200">
              {filteredPatients.map((patient: any) => (
                <div
                  key={patient.id}
                  className={`p-4 flex items-center hover:bg-gray-50 cursor-pointer ${
                    selectedPatients.includes(patient.id) ? "bg-green-50" : ""
                  }`}
                  onClick={() => togglePatient(patient.id)}
                >
                  <div className="mr-4">
                    {selectedPatients.includes(patient.id) ? (
                      <CheckSquare className="h-5 w-5 text-green-600" />
                    ) : (
                      <Square className="h-5 w-5 text-gray-400" />
                    )}
                  </div>

                  <div className="flex-1">
                    <div className="flex flex-col sm:flex-row sm:items-center justify-between">
                      <div className="flex items-center gap-3">
                        <div className="bg-green-100 p-2 rounded-full">
                          <User className="h-5 w-5 text-green-600" />
                        </div>
                        <div>
                          <h3 className="font-medium text-gray-900">
                            {patient.nome}
                          </h3>
                          <div className="text-sm text-gray-500 flex flex-col sm:flex-row sm:gap-3">
                            <span>CPF: {patient.cpf}</span>
                            {patient.idade && (
                              <span>• {patient.idade} anos</span>
                            )}
                            {patient.tipo_sanguineo && (
                              <span className="bg-green-50 text-green-700 px-2 py-0.5 rounded-full text-xs inline-block sm:inline">
                                {patient.tipo_sanguineo}
                              </span>
                            )}
                          </div>
                        </div>
                      </div>

                      <div className="mt-2 sm:mt-0 flex items-center gap-2">
                        <span className="text-sm text-gray-500">
                          {patient.listaIndicesPulmonares?.length || 0} índices
                          pulmonares
                        </span>
                        <span className="text-sm text-gray-500">
                          {patient.listaIndicesCardiacos?.length || 0} índices
                          cardíacos
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="p-8 text-center">
              <p className="text-gray-500">
                Nenhum paciente encontrado com o termo "{searchTerm}"
              </p>
            </div>
          )}

          <div className="bg-gray-50 p-4 border-t border-gray-200 flex justify-between items-center">
            <span className="text-sm text-gray-500">
              {filteredPatients.length} pacientes encontrados
            </span>
            <span className="text-sm text-gray-500">
              {selectedPatients.length} selecionados para exportação
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
