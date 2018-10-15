package fr.glowstoner.kim.executor;

public class PackageExecutor implements Executor{

	private PackageInfo packageInfo;
	
	public PackageExecutor(PackageInfo packageInfo) {
		this.setPackageInfo(packageInfo);
	}
	
	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	@Override
	public void execute() {
		
	}
}
