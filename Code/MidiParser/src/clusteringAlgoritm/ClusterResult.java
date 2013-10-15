package clusteringAlgoritm;

public class ClusterResult {
	MidiDataCentroid[] clusters;
	int occurances = 0;
	
	public ClusterResult(MidiDataCentroid[] centroids){
		this.clusters = centroids;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ClusterResult){
			int count = 0;
			ClusterResult otherResult = (ClusterResult) obj;
			
			for(MidiDataCentroid otherC : otherResult.clusters)
				for(MidiDataCentroid thisC : clusters){
					if(otherC.pointsInCluster.size() == thisC.pointsInCluster.size()){
						int size = 0;
						for(int i = 0; i< otherC.pointsInCluster.size(); i++) { 
							for(int j = 0; j < thisC.pointsInCluster.size(); j++){
								if(thisC.pointsInCluster.get(j).getFileName().equalsIgnoreCase(otherC.pointsInCluster.get(i).getFileName()))
									size++;		
							}
						}
						if(size == thisC.pointsInCluster.size())
							count++;
					}
				}
			
			return count == Clumper.NUMBER_OF_ERAS;
		}
		else return false;
	}
}
