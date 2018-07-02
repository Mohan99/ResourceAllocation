package com.gcs.db.tool;

public class MSSQLColumnMetadata {
	private String columnName;
	private String columnLabel;
	private String tableName;
	private String columnTypeName;
	private String columnCatalogueName;
	private String columnClassName;
	private String schemaName;
	
	private int columnDisplaySize;
	private int columnType;
	private int precission;
	private int scale;
	
	private boolean nullable;
	private boolean autoIncrement;
	private boolean currency;
	private boolean definitelyWritable;
	private boolean caseSensitive;
	private boolean readOnly;
	private boolean searchable;
	private boolean signed;
	private boolean writable;
	
	private static String NEW_LINE = "\n";
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Column Name: ").append(columnName).append(NEW_LINE)
		.append("Column Label: ").append(columnLabel).append(NEW_LINE)
		.append("Table Name: ").append(tableName).append(NEW_LINE)
		.append("Column Type Name: ").append(columnTypeName).append(NEW_LINE)
		.append("Column Catalogue Name: ").append(columnCatalogueName).append(NEW_LINE)
		.append("Column Class Name: ").append(columnClassName).append(NEW_LINE)
		.append("Schema Name: ").append(schemaName).append(NEW_LINE)
		.append("Column Display Size: ").append(columnDisplaySize).append(NEW_LINE)
		.append("Column Type: ").append(columnType).append(NEW_LINE)
		.append("Precission: ").append(precission).append(NEW_LINE)
		.append("Scale: ").append(scale).append(NEW_LINE)
		.append("Is Nullable: ").append(nullable).append(NEW_LINE)
		.append("Is Auto Increment: ").append(autoIncrement).append(NEW_LINE)
		.append("Is Currency: ").append(currency).append(NEW_LINE)
		.append("Is Definitely Writable: ").append(definitelyWritable).append(NEW_LINE)
		.append("Is Read Only: ").append(readOnly).append(NEW_LINE)
		.append("Is Searchable: ").append(searchable).append(NEW_LINE)
		.append("Is Signed: ").append(signed).append(NEW_LINE)
		.append("Is Case Sensitive: ").append(caseSensitive).append(NEW_LINE)
		.append("Is Writable: ").append(writable);
		return sb.toString();
	}
	
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the columnLabel
	 */
	public String getColumnLabel() {
		return columnLabel;
	}
	/**
	 * @param columnLabel the columnLabel to set
	 */
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the columnTypeName
	 */
	public String getColumnTypeName() {
		return columnTypeName;
	}
	/**
	 * @param columnTypeName the columnTypeName to set
	 */
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	/**
	 * @return the columnCatalogueName
	 */
	public String getColumnCatalogueName() {
		return columnCatalogueName;
	}
	/**
	 * @param columnCatalogueName the columnCatalogueName to set
	 */
	public void setColumnCatalogueName(String columnCatalogueName) {
		this.columnCatalogueName = columnCatalogueName;
	}
	/**
	 * @return the columnClassName
	 */
	public String getColumnClassName() {
		return columnClassName;
	}
	/**
	 * @param columnClassName the columnClassName to set
	 */
	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}
	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}
	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	/**
	 * @return the columnDisplaySize
	 */
	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}
	/**
	 * @param columnDisplaySize the columnDisplaySize to set
	 */
	public void setColumnDisplaySize(int columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}
	/**
	 * @return the columnType
	 */
	public int getColumnType() {
		return columnType;
	}
	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	/**
	 * @return the precission
	 */
	public int getPrecission() {
		return precission;
	}
	/**
	 * @param precission the precission to set
	 */
	public void setPrecission(int precission) {
		this.precission = precission;
	}
	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return the autoIncrement
	 */
	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	/**
	 * @param autoIncrement the autoIncrement to set
	 */
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	/**
	 * @return the currency
	 */
	public boolean isCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(boolean currency) {
		this.currency = currency;
	}

	/**
	 * @return the definitelyWritable
	 */
	public boolean isDefinitelyWritable() {
		return definitelyWritable;
	}

	/**
	 * @param definitelyWritable the definitelyWritable to set
	 */
	public void setDefinitelyWritable(boolean definitelyWritable) {
		this.definitelyWritable = definitelyWritable;
	}

	/**
	 * @return the caseSensitive
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * @param caseSensitive the caseSensitive to set
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the searchable
	 */
	public boolean isSearchable() {
		return searchable;
	}

	/**
	 * @param searchable the searchable to set
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	/**
	 * @return the signed
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * @param signed the signed to set
	 */
	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	/**
	 * @return the writable
	 */
	public boolean isWritable() {
		return writable;
	}

	/**
	 * @param writable the writable to set
	 */
	public void setWritable(boolean writable) {
		this.writable = writable;
	}
}
